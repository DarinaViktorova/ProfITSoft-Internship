import React, { useState, useEffect } from 'react';
import { Select, MenuItem, FormControl, InputLabel, Box, Button } from '@mui/material';

const FilterComponent = ({ onFilter, missions }) => {
  const [filterValues, setFilterValues] = useState(() => {
    const savedFilters = localStorage.getItem('missionListFilters');
    return savedFilters ? JSON.parse(savedFilters) : { planetName: '', missionYear: '' };
  });
  const [uniquePlanets, setUniquePlanets] = useState([]);
  const [uniqueYears, setUniqueYears] = useState([]);

  useEffect(() => {
    const planets = [...new Set(missions.map(mission => mission.planetName))];
    const years = [...new Set(missions.map(mission => mission.missionYear))];
    years.sort((a, b) => a - b); 

    setUniquePlanets(planets);
    setUniqueYears(years);
  }, [missions]);

  useEffect(() => {
    localStorage.setItem('missionListFilters', JSON.stringify(filterValues));
  }, [filterValues]);

  const handleChange = (event) => {
    const { name, value } = event.target;
    const newFilterValues = { ...filterValues, [name]: value };
    setFilterValues(newFilterValues);
    onFilter(newFilterValues);
  };

  const handleReset = () => {
    setFilterValues({ planetName: '', missionYear: '' });
    onFilter({ planetName: '', missionYear: '' });
  };

  return (
    <Box display="flex" flexDirection="row" gap={2} mt={2}>
      <FormControl variant="outlined" sx={{ minWidth: 200 }}>
        <InputLabel>Planet Name</InputLabel>
        <Select
          name="planetName"
          value={filterValues.planetName}
          onChange={handleChange}
          label="Planet Name"
        >
          <MenuItem value=""><em>None</em></MenuItem>
          {uniquePlanets.map((planet, index) => (
            <MenuItem key={index} value={planet}>{planet}</MenuItem>
          ))}
        </Select>
      </FormControl>
      <FormControl variant="outlined" sx={{ minWidth: 200 }}>
        <InputLabel>Mission Year</InputLabel>
        <Select
          name="missionYear"
          value={filterValues.missionYear}
          onChange={handleChange}
          label="Mission Year"
        >
          <MenuItem value=""><em>None</em></MenuItem>
          {uniqueYears.map((year, index) => (
            <MenuItem key={index} value={year}>{year}</MenuItem>
          ))}
        </Select>
      </FormControl>
      <Button onClick={handleReset} variant="contained">Reset</Button>
    </Box>
  );
};

export default FilterComponent;
