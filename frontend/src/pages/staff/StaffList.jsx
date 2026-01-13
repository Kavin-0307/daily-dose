import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import staffApi from "../../api/staffApi.js";
import "../receipts/receipts.css";

function StaffList() {
  const navigate = useNavigate();

  const [staff, setStaff] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const currentEmail = localStorage.getItem("email");

  useEffect(() => {
    const loadStaff = async () => {
      try {
        setLoading(true);
        const data = await staffApi.getAllStaff();
        setStaff(data);
      } catch (err) {
        console.error(err);
        setError("Failed to load staff");
      } finally {
        setLoading(false);
      }
    };

    loadStaff();
  }, []);

  const filteredStaff = staff.filter((s) => {
    const term = searchTerm.toLowerCase();
    return (
      s.staffName?.toLowerCase().includes(term) ||
      s.staffEmail?.toLowerCase().includes(term)
    );
  });

  if (loading) return <p>Loading staff…</p>;
  if (error) return <p style={{ color: "red" }}>{error}</p>;

  const handleDeactivate = async (id) => {
    try {
      await staffApi.deactivateStaff(id);
      setStaff((prev) => prev.filter((s) => s.staffId !== id));
    } catch (err) {
      console.error(err);
      alert(err.response?.data?.message || "Action not allowed");
    }
  };

  return (
    <section className="receipt">
      {/* Header */}
      <div className="receipt-header">
        <h1>Staff</h1>

        <button
          className="receipt-primary-btn"
          onClick={() => navigate("/staff/create")}
        >
          + Create Staff
        </button>
      </div>

      <h3>Active Staff</h3>

      <input
        type="text"
        placeholder="Search by name or email"
        value={searchTerm}
        className="receipt-search"
        onChange={(e) => setSearchTerm(e.target.value)}
        style={{ marginBottom: "16px", padding: "8px" }}
      />

      {filteredStaff.length === 0 ? (
        <p>No staff found.</p>
      ) : (
        <ul className="receipt-grid">
          {filteredStaff.map((s) => (
            <li key={s.staffId} className="receipt-card">
              <p><strong>Name:</strong> {s.staffName}</p>
              <p><strong>Email:</strong> {s.staffEmail}</p>
              <p>
                <strong>Status:</strong>{" "}
                {s.staffActive ? "Active" : "Inactive"}
              </p>
                
            <button
  className="receipt-secondary-btn"
  onClick={() => navigate(`/staff/${s.staffId}/patients`)}
>
  View Patients
</button>

              {s.staffEmail !== currentEmail && (
                <button
                  className="receipt-secondary-btn"
                  onClick={() => handleDeactivate(s.staffId)}
                >
                  Deactivate
                </button>
              )}
            </li>
          ))}
        </ul>
      )}
    </section>
  );
}

export default StaffList;
