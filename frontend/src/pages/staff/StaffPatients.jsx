import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import patientApi from "../../api/patientApi";
import "../receipts/receipts.css";

function StaffPatients() {
  const { staffId } = useParams();
  const navigate = useNavigate();

  const [patients, setPatients] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const loadPatients = async () => {
      try {
        const data = await patientApi.getPatientsByStaff(staffId);
        setPatients(data);
      } catch (err) {
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    loadPatients();
  }, [staffId]);

  if (loading) return <p>Loading patients…</p>;

  return (
    <section className="receipt">
      <div className="receipt-header">
        <h1>Patients Under Staff</h1>

        <button
          className="receipt-secondary-btn"
          onClick={() => navigate("/staff")}
        >
          ← Back to Staff
        </button>
      </div>

      {patients.length === 0 ? (
        <p>No patients assigned.</p>
      ) : (
        <ul className="receipt-grid">
          {patients.map((p) => (
            <li
              key={p.patientId}
              className="receipt-card"
              onClick={() => navigate(`/patients/${p.patientId}`)}
            >
              <p><strong>{p.patientName}</strong></p>
              <p className="receipt-meta">
                Age: {p.patientAge} • {p.patientGender}
              </p>
              <p className="receipt-meta">
                Phone: {p.patientPhone}
              </p>
            </li>
          ))}
        </ul>
      )}
    </section>
  );
}

export default StaffPatients;
