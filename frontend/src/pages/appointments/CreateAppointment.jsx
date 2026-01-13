import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import appointmentApi from "../../api/appointmentApi";
import staffApi from "../../api/staffApi";
import patientApi from "../../api/patientApi";
import "../receipts/receipts.css";

function CreateAppointment() {
  const navigate = useNavigate();

  const [staffList, setStaffList] = useState([]);
  const [patients, setPatients] = useState([]);

  const [staffId, setStaffId] = useState("");
  const [patientId, setPatientId] = useState("");
  const [appointmentTime, setAppointmentTime] = useState("");
  const [reason, setReason] = useState("");

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    staffApi.getAllStaff().then(setStaffList);
    patientApi.getAllPatients().then(setPatients);
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      setLoading(true);
      setError("");

      await appointmentApi.createAppointment({
        staffId: Number(staffId),
        patientId: Number(patientId),
        appointmentTime,
        reason
      });

      navigate("/appointments");
    } catch (err) {
      console.error(err);
      setError(err.response?.data?.message || "Failed to create appointment");
    } finally {
      setLoading(false);
    }
  };

  return (
    <section className="receipt">
      <div className="receipt-header">
        <h1>Create Appointment</h1>
      </div>

      {error && <p style={{ color: "red" }}>{error}</p>}

      <form onSubmit={handleSubmit}>
        <div className="receipt-card receipt-form">

          <select value={patientId} onChange={e => setPatientId(e.target.value)} required>
            <option value="">Select Patient</option>
            {patients.map(p => (
              <option key={p.patientId} value={p.patientId}>
                {p.patientName}
              </option>
            ))}
          </select>

          <select value={staffId} onChange={e => setStaffId(e.target.value)} required>
            <option value="">Select Staff</option>
            {staffList.map(s => (
              <option key={s.staffId} value={s.staffId}>
                {s.staffName}
              </option>
            ))}
          </select>

          <input
            type="datetime-local"
            value={appointmentTime}
            onChange={e => setAppointmentTime(e.target.value)}
            required
          />

          <textarea
            placeholder="Reason for appointment"
            value={reason}
            onChange={e => setReason(e.target.value)}
            rows={3}
          />

          <div className="receipt-form-row">
            <button
              type="submit"
              className="receipt-primary-btn"
              disabled={loading}
            >
              {loading ? "Saving..." : "Create Appointment"}
            </button>
          </div>

        </div>
      </form>
    </section>
  );
}

export default CreateAppointment;
