import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Card, CardContent, Typography, Button, Box, TextField, IconButton } from '@mui/material';
import { missions as initialMissions } from '_mock/mockData'; 
import PageContainer from 'pageProviders/components/PageContainer';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import { Snackbar } from '@mui/material';

const MissionDetail = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [mission, setMission] = useState(null);
    const [isEditing, setIsEditing] = useState(id === 'new');
    const [isCreating, setIsCreating] = useState(id === 'new');

    const [snackbarOpen, setSnackbarOpen] = useState(false);
    const [snackbarMessage, setSnackbarMessage] = useState("");
    const [snackbarError, setSnackbarError] = useState(false);

    useEffect(() => {
        if (id !== 'new') {
            const missionData = initialMissions.find(mission => mission.id === id);
            if (missionData) {
                setMission(missionData);
            } else {
                setMission(null);
            }
        } else {
            setMission({ id: `${initialMissions.length + 1}`, planetName: '', missionYear: '', spaceships: [] });
        }
    }, [id]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setMission(prevMission => ({ ...prevMission, [name]: value }));
    };

    const handleAddSpaceship = () => {
        const updatedSpaceships = [...mission.spaceships, { id: `${mission.spaceships.length + 1}`, spaceshipName: '', destinationPlanet: '', capacity: '', missionId: mission.id }];
        setMission(prevMission => ({ ...prevMission, spaceships: updatedSpaceships }));
    };

    const handleDeleteSpaceship = (id) => {
        const updatedSpaceships = mission.spaceships.filter(spaceship => spaceship.id !== id);
        setMission(prevMission => ({ ...prevMission, spaceships: updatedSpaceships }));
    };

    const handleSpaceshipChange = (e, spaceshipId) => {
        const { name, value } = e.target;
        const updatedSpaceships = mission.spaceships.map(spaceship =>
            spaceship.id === spaceshipId ? { ...spaceship, [name]: value } : spaceship
        );
        setMission(prevMission => ({ ...prevMission, spaceships: updatedSpaceships }));
    };

    const handleSave = () => {
        if (validateFields()) {
          try {
            if (id === "new") {
              initialMissions.push(mission);
            } else {
              const missionIndex = initialMissions.findIndex((m) => m.id === id);
              initialMissions[missionIndex] = mission;
            }
            setMission(mission);
            setIsEditing(false);
            setIsCreating(false);
            setSnackbarMessage("Mission saved successfully");
            setSnackbarError(false);
            setSnackbarOpen(true);
            navigate("/missions");
          } catch (error) {
            console.error("Error saving mission:", error);
            setSnackbarMessage("Error saving mission");
            setSnackbarError(true);
            setSnackbarOpen(true);
          }
        }
      };

      const handleCancel = () => {
        if (isCreating) {
          navigate("/missions");
        } else {
          setIsEditing(false);
          setMission(initialMissions.find((m) => m.id === id));
        }
      };
      

    const handleCloseSnackbar = () => {
        setSnackbarOpen(false);
        setSnackbarError(false);
    };

    const handleBackClick = () => {
        navigate('/missions');
    };

    const validateFields = () => {
        if (!mission.planetName || !mission.missionYear) {
            setSnackbarMessage("All fields are required");
            setSnackbarError(true);
            setSnackbarOpen(true);
            return false;
        }
        return true;
    };

    if (!mission) return <div>Mission not found</div>;

    return (
        <PageContainer>
        <Box m={2}>
            <Button onClick={handleBackClick} variant="outlined" style={{ marginBottom: "20px" }}>
                Back
            </Button>

            <Card variant="outlined">
                <CardContent>
                    {isEditing ? (
                        <Box>
                            <Typography variant="h5" component="div">
                                {isCreating ? "Create Mission" : "Edit Mission"}
                            </Typography>

                            <Box mt={2}>
                                <TextField
                                    name="planetName"
                                    label="Planet Name"
                                    value={mission.planetName}
                                    onChange={handleChange}
                                    fullWidth
                                    sx={{ mb: 2 }}
                                    required
                                />

                                <TextField
                                    name="missionYear"
                                    label="Mission Year"
                                    value={mission.missionYear}
                                    onChange={handleChange}
                                    fullWidth
                                    sx={{ mb: 2 }}
                                    required
                                />

                                <Typography variant="h6" component="div">
                                    Spaceships:
                                </Typography>

                                {mission.spaceships.map((spaceship) => (
                                    <Box key={spaceship.id} sx={{ mb: 2 }}>
                                        <TextField
                                            name="spaceshipName"
                                            label="Spaceship Name"
                                            value={spaceship.spaceshipName}
                                            onChange={(e) => handleSpaceshipChange(e, spaceship.id)}
                                            fullWidth
                                            sx={{ mb: 1 }}
                                        />

                                        <TextField
                                            name="destinationPlanet"
                                            label="Destination Planet"
                                            value={spaceship.destinationPlanet}
                                            onChange={(e) => handleSpaceshipChange(e, spaceship.id)}
                                            fullWidth
                                            sx={{ mb: 1 }}
                                        />

                                        <TextField
                                            name="capacity"
                                            label="Capacity"
                                            value={spaceship.capacity}
                                            onChange={(e) => handleSpaceshipChange(e, spaceship.id)}
                                            fullWidth
                                            sx={{ mb: 1 }}
                                        />

                                        <IconButton onClick={() => handleDeleteSpaceship(spaceship.id)} color="error">
                                            <DeleteIcon />
                                        </IconButton>
                                    </Box>
                                ))}

                                <Button onClick={handleAddSpaceship} variant="contained" color="primary" sx={{ mr: 2 }}>
                                    Add Spaceship
                                </Button>

                                <Button onClick={handleSave} variant="contained" color="primary">
                                    {isCreating ? "Create" : "Save"}
                                </Button>

                                <Button onClick={handleCancel} variant="contained" sx={{ ml: 2 }}>
                                    Cancel
                                </Button>
                            </Box>
                        </Box>
                    ) : (
                        <Box>
                            <Typography variant="h5" component="div">
                                {mission.planetName}
                                <IconButton onClick={() => setIsEditing(true)} color="primary" style={{ float: "right" }}>
                                    <EditIcon />
                                </IconButton>
                            </Typography>

                            <Typography variant="body2" color="text.secondary">
                                Mission Year: {mission.missionYear}
                            </Typography>

                            <Box mt={2}>
                                <Typography variant="h6" component="div">
                                    Spaceships:
                                </Typography>
                                {mission.spaceships.map((spaceship) => (
                                    <Typography key={spaceship.id} variant="body2" color="text.secondary">
                                        {spaceship.spaceshipName} - {spaceship.destinationPlanet} (Capacity: {spaceship.capacity})
                                    </Typography>
                                ))}
                            </Box>
                        </Box>
                    )}
                </CardContent>
            </Card>
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

export default MissionDetail;