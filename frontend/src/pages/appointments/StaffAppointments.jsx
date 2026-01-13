// src/pages/appointments/StaffAppointments.jsx
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import appointmentApi from "../../api/appointmentApi";
import "../receipts/receipts.css";

function StaffAppointments() {
  const { staffId } = useParams();
  const [appointments, setAppointments] = useState([]);

  useEffect(() => {
    appointmentApi.getAppointmentsByStaff(staffId)
      .then(setAppointments);
  }, [staffId]);

  return (
    <section className="receipt">
      <div className="receipt-header">
        <h1>Appointments</h1>
      </div>

      {appointments.length === 0 ? (
        <p>No appointments.</p>
      ) : (
        <ul className="receipt-grid">
          {appointments.map(a => (
            <li key={a.appointmentId} className="receipt-card">
              <p><strong>Patient:</strong> {a.patientName}</p>
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

export default StaffAppointments;
