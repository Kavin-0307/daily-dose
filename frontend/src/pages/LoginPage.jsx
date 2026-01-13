import { useState } from "react";
import { useNavigate } from "react-router-dom";
import authApi from "../api/authApi";

function LoginPage() {

  //Form state (like variables bound to inputs)
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  //UI state
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  //Navigation helper
  const navigate = useNavigate();

  //Form submit handler
  const handleSubmit = async (e) => {
    e.preventDefault(); // stops page reload

    setError("");
    setLoading(true);

    try {
      // Call backend login
      await authApi.login(email, password);
      // On success → go to dashboard
      navigate("/");
    } catch (error) 
    {
      console.error(error);
      // Login failed
      setError("Invalid email or password");
    } finally {
      setLoading(false);
    }
  };

  // HTML (JSX)
  return (
    <div style={{ maxWidth: "300px", margin: "100px auto" }}>
      <h2>Login</h2>

      {error && <p style={{ color: "red" }}>{error}</p>}

      <form onSubmit={handleSubmit}>

        <div>
          <label>Email</label><br />
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>

        <div>
          <label>Password</label><br />
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>

        <br />

        <button type="submit" disabled={loading}>
          {loading ? "Logging in..." : "Login"}
        </button>

      </form>
    </div>
  );
}

export default LoginPage;
