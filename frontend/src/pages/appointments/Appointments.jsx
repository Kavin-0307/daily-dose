import { useEffect, useState } from "react";
import appointmentApi from "../../api/appointmentApi";
import "../receipts/receipts.css";
import { useNavigate } from "react-router-dom";

function Appointments() {
  const [appointments, setAppointments] = useState([]);
const navigate = useNavigate();

  useEffect(() => {
    appointmentApi.getAllAppointments().then(setAppointments);
  }, []);

  return (
    <section className="receipt">
      <div className="receipt-header">
  <h1>All Appointments</h1>

  <button
    className="receipt-primary-btn"
    onClick={() => navigate("/appointments/create")}
  >
    + Create Appointment
  </button>
</div>

      {appointments.length === 0 ? (
        <p>No appointments.</p>
      ) : (
        <ul className="receipt-grid">
          {appointments.map(a => (
            <li key={a.appointmentId} className="receipt-card">
              <p><strong>Patient:</strong> {a.patientName}</p>
              <p><strong>Staff:</strong> {a.staffName}</p>
              <p className="receipt-meta">
                {new Date(a.appointmentTime).toLocaleString()}
              </p>
              <p className="receipt-meta">
                Status: {a.status}
              </p>
            </li>
          ))}
        </ul>
      )}
    </section>
  );
}

export default Appointments;
