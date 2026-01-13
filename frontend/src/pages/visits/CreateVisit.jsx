import { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import staffApi from "../../api/staffApi";
import visitApi from "../../api/visitApi";

function CreateVisit() {
  const { id } = useParams(); // patientId
  const navigate = useNavigate();

  const [notes, setNotes] = useState("");
  const [staffList, setStaffList] = useState([]);
  const [staffId, setStaffId] = useState("");

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    staffApi.getAllStaff().then(setStaffList);
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      setLoading(true);

      await visitApi.createVisit({
        patientId: Number(id),
        staffId: Number(staffId),
        visitDate: new Date().toISOString(),
        treatmentNotes: notes
      });

      navigate(`/patients/${id}`);
    } catch (err) {
      console.error(err);
      setError("Failed to create visit");
    } finally {
      setLoading(false);
    }
  };

  return (
    <section className="patient">
      <div className="patient-header">
        <div className="patient-header-text">
          <h1>Add Visit</h1>
          <p>Record clinical notes for this patient</p>
        </div>
      </div>

      {error && <p style={{ color: "red" }}>{error}</p>}

      <form onSubmit={handleSubmit}>
  <div className="receipt-card">
    <div className="receipt-form">

      {/* Clinical Notes – full width */}
      <div className="receipt-form-row">
        <label style={{ fontWeight: 600 }}>Clinical Notes</label>
        <textarea
          value={notes}
          onChange={(e) => setNotes(e.target.value)}
          rows={5}
          placeholder="Describe symptoms, observations, treatment, and follow-up..."
          required
        />
      </div>

      {/* Assign Staff */}
      <div>
        <label style={{ fontWeight: 600 }}>Assign Staff</label>
        <select
          value={staffId}
          onChange={(e) => setStaffId(e.target.value)}
          required
        >
          <option value="">Select Staff</option>
          {staffList.map((s) => (
            <option key={s.staffId} value={s.staffId}>
              {s.staffName}
            </option>
          ))}
        </select>
      </div>

    </div>

    {/* Buttons */}
    <div style={{ marginTop: "24px", display: "flex", gap: "12px" }}>
      <button
        type="submit"
        className="receipt-primary-btn"
        disabled={loading}
      >
        {loading ? "Saving..." : "Create Visit"}
      </button>

      <button
        type="button"
        className="receipt-secondary-btn"
        onClick={() => navigate(`/patients/${id}`)}
      >
        Cancel
      </button>
    </div>
  </div>
</form>

    </section>
  );
}

export default CreateVisit;
