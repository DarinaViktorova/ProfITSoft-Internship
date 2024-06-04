import React, { useState } from 'react';
import { Dialog, DialogActions, DialogContent, Button, Typography } from '@mui/material';
import deleteIcon from './icon/delete.png'; // Импорт изображения PNG

const DeleteButton = ({ onDelete }) => {
  const [open, setOpen] = useState(false);

  const handleOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleDelete = () => {
    onDelete();
    setOpen(false);
  };

  return (
    <>
      <img
        src={deleteIcon}
        alt="Delete"
        onClick={handleOpen}
        style={{ cursor: 'pointer', width: '30px', position: 'absolute', top: '5px', right: '5px' }}
      /> 
      <Dialog open={open} onClose={handleClose}>
        <DialogContent>
          <Typography variant="body1">Are you sure you want to delete this entity?</Typography>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} color="primary">Cancel</Button>
          <Button onClick={handleDelete} color="error">Delete</Button>
        </DialogActions>
      </Dialog>
    </>
  );
};

export default DeleteButton;
