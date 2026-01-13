import { useState } from "react";
import { useNavigate } from "react-router-dom";
import staffApi from "../../api/staffApi";
import "../receipts/receipts.css";

function CreateStaff() {
  const navigate = useNavigate();

  const [staffName, setStaffName] = useState("");
  const [staffEmail, setStaffEmail] = useState("");
  const [password, setPassword] = useState("");

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      setLoading(true);
      setError("");

      await staffApi.createStaff({
        staffName,
        staffEmail,
        password,
      });

      navigate("/staff");
    } catch (err) {
      console.error(err);
      setError(err.response?.data?.message || "Failed to create staff");
    } finally {
      setLoading(false);
    }
  };

  return (
    <section className="receipt">
      {/* Header */}
      <div className="receipt-header">
        <div className="receipt-header-text">
          <h1>Create Staff</h1>
          <p>Add a new staff member</p>
        </div>

        <button
          className="receipt-secondary-btn"
          onClick={() => navigate("/staff")}
        >
          ← Back to Staff
        </button>
      </div>

      {error && <p style={{ color: "red", marginBottom: "12px" }}>{error}</p>}

      <form onSubmit={handleSubmit}>
        <div className="receipt-card">
          <div className="receipt-form">
            {/* Row 1 */}
            <input
              type="text"
              value={staffName}
              onChange={(e) => setStaffName(e.target.value)}
              placeholder="Enter staff name"
              required
            />

            <input
              type="email"
              value={staffEmail}
              onChange={(e) => setStaffEmail(e.target.value)}
              placeholder="Enter staff email"
              required
            />

            {/* Row 2 (full width) */}
            <div className="receipt-form-row">
              <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="Temporary password"
                required
              />
            </div>
          </div>

          {/* Actions */}
          <div style={{ marginTop: "20px", display: "flex", gap: "12px" }}>
            <button
              type="submit"
              className="receipt-primary-btn"
              disabled={loading}
            >
              {loading ? "Creating..." : "Create Staff"}
            </button>

            <button
              type="button"
              onClick={() => navigate("/staff")}
              style={{
                background: "transparent",
                border: "none",
                color: "#6b7280",
                cursor: "pointer",
              }}
            >
              Cancel
            </button>
          </div>
        </div>
      </form>
    </section>
  );
}

export default CreateStaff;
