import { createContext, useContext, useState, useEffect } from 'react';
const AuthContext = createContext();

export const useAuth = () => {
  return useContext(AuthContext);
};

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [userId, setUserId] = useState(null);

  useEffect(() => {
    const storedUsername = localStorage.getItem('username');
    const storedUserId = localStorage.getItem('userId');
    if (storedUsername && storedUserId) {
      setUser(storedUsername);
      setUserId(storedUserId);
    }
  }, []);

  const login = (username, id) => {
    setUser(username);
    setUserId(id);
    localStorage.setItem('username', username);
    localStorage.setItem('userId', id);
  };

  const logout = () => {
    setUser(null);
    setUserId(null);
    localStorage.removeItem('username');
    localStorage.removeItem('userId');
  };

  return <AuthContext.Provider value={{ user, userId, login, logout }}>{children}</AuthContext.Provider>;
};
