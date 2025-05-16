import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Register from './auth/RegisterPage';
import Login from './auth/LoginPage';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Navigate to="/register" replace />} />
        <Route path="/register" element={<Register />} />
        <Route path="/login" element={<Login />} />
      </Routes>
    </Router>
  );
}

export default App;
