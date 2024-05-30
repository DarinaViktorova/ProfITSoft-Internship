import React, { useState, useContext, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import { missions as initialMissions } from "../../_mock/mockData";
import PageContainer from "pageProviders/components/PageContainer";
import Typography from "components/Typography";
import { Button, Card, CardContent, Box, Snackbar } from "@mui/material";
import SmallButton from "components/SmallButton/SmallButtom";
import DeleteButton from "components/DeleteButton/DeleteButton";
import FilterComponent from "components/FilterComponent/FilterComponent";
import { UserContext } from "misc/providers/UserProvider";
import { paginate } from "./components/paginate";

const MissionList = () => {
    const user = useContext(UserContext);
    const navigate = useNavigate();

    const [missions, setMissions] = useState(initialMissions);
    const [hoveredMissionIndex, setHoveredMissionIndex] = useState(null);
    const [filteredMissions, setFilteredMissions] = useState(missions);

    const [currentPage, setCurrentPage] = useState(() => {
        const savedPage = localStorage.getItem("currentPage");
        return savedPage ? parseInt(savedPage, 10) : 0;
      });
      
    const itemsPerPage = 5;
    const paginatedData = paginate(filteredMissions, itemsPerPage);
    const currentMissions = paginatedData[currentPage] || [];

    const [snackbarOpen, setSnackbarOpen] = useState(false);
    const [snackbarMessage, setSnackbarMessage] = useState("");
    const [snackbarError, setSnackbarError] = useState(false);

    useEffect(() => {
      setFilteredMissions(missions);
    }, [missions]);

    useEffect(() => {
      localStorage.setItem("currentPage", currentPage);
    }, [currentPage]);

    useEffect(() => {
        const savedFilterValues = JSON.parse(localStorage.getItem("filterValues"));
        if (savedFilterValues) {
          handleFilter(savedFilterValues);
        }
      }, []);

    const handleAddMission = () => {
      const newMission = { id: `${missions.length + 1}`, planetName: "", missionYear: "", spaceships: [] };
      const updatedMissions = [...missions, newMission];
      setMissions(updatedMissions);
      navigate("/missions/new");
    };

    const handleDeleteMission = (id) => {
      try {
        const updatedMissions = missions.filter(mission => mission.id !== id);
        setMissions(updatedMissions);
        setSnackbarMessage("Mission deleted successfully");
        setSnackbarOpen(true);
      } catch (error) {
        console.error("Error deleting mission:", error);
        setSnackbarMessage("Error deleting mission");
        setSnackbarError(true);
        setSnackbarOpen(true);
      }
    };

    const handleFilter = (filterValues) => {
      let filteredMissions = missions;

      if (filterValues.planetName) {
        filteredMissions = filteredMissions.filter(
          (mission) => mission.planetName === filterValues.planetName
        );
      }

      if (filterValues.missionYear) {
        filteredMissions = filteredMissions.filter(
          (mission) => mission.missionYear === filterValues.missionYear
        );
      }

      setFilteredMissions(filteredMissions);
      setCurrentPage(0);
      localStorage.setItem("filterValues", JSON.stringify(filterValues));
      localStorage.setItem("currentPage", 0);
    };

    useEffect(() => {
      const savedFilterValues = JSON.parse(localStorage.getItem("filterValues"));
      if (savedFilterValues) {
        handleFilter(savedFilterValues);
      }
    }, []);

    const goToPreviousPage = () => {
      setCurrentPage((prevPage) => Math.max(prevPage - 1, 0));
    };

    const goToNextPage = () => {
      setCurrentPage((prevPage) =>
        Math.min(prevPage + 1, paginatedData.length - 1)
      );
    };

    const handleCloseSnackbar = () => {
      setSnackbarOpen(false);
      setSnackbarError(false);
    };

    useEffect(() => {
        localStorage.setItem("missions", JSON.stringify(missions));
    }, [missions]);

    useEffect(() => {
        localStorage.setItem("currentPage", currentPage);
    }, [currentPage]);

    useEffect(() => {
        localStorage.setItem("filteredMissions", JSON.stringify(filteredMissions));
    }, [filteredMissions]);

    return (
      <PageContainer>
        <Typography>
          <h1>Mission List</h1>
        </Typography>

        {user.id && (
          <Button onClick={handleAddMission} variant="contained" color="primary">
            Add Mission
          </Button>
        )}

        <FilterComponent onFilter={handleFilter} missions={missions} />

        <Box mt={2}>
          {currentMissions.map((mission) => (
            <Card
              key={mission.id}
              variant="outlined"
              sx={{ mb: 2 }}
              onMouseEnter={() => setHoveredMissionIndex(mission.id)}
              onMouseLeave={() => setHoveredMissionIndex(null)}
            >
              <CardContent style={{ position: "relative" }}>
                <Typography variant="h6">
                  <Link to={`/missions/${mission.id}`}>
                    {mission.planetName} - {mission.missionYear}
                  </Link>
                  {user.id && hoveredMissionIndex === mission.id && (
                    <DeleteButton onDelete={() => handleDeleteMission(mission.id)} />
                  )}
                </Typography>
              </CardContent>
            </Card>
          ))}
        </Box>

        <Box mt={2} display="flex" justifyContent="center">
          <SmallButton
            disabled={currentPage === 0}
            onClick={goToPreviousPage}
            variant="contained"
            color="primary"
          >
            Previous
          </SmallButton>
          <SmallButton
            disabled={currentPage === paginatedData.length - 1}
            onClick={goToNextPage}
            variant="contained"
            color="primary"
            sx={{ ml: 2 }}
          >
            Next
          </SmallButton>
        </Box>

        <Snackbar
          open={snackbarOpen}
          autoHideDuration={6000}
          onClose={handleCloseSnackbar}
          message={snackbarMessage}
          anchorOrigin={{ vertical: "top", horizontal: "right" }}
          severity={snackbarError ? "error" : "success"}
        />
      </PageContainer>
    );
};

export default MissionList;
