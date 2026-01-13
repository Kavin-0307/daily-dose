import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";

import "./patients.css";
import patientApi from "../../api/patientApi";
import visitApi from "../../api/visitApi";

function PatientDetail() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [patient, setPatient] = useState(null);
  const [visits, setVisits] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const loadPatientDetails = async () => {
      try {
        setLoading(true);

        const patientData = await patientApi.getPatientById(id);
        setPatient(patientData);

        const visitData = await visitApi.getVisitsByPatient(id);
        setVisits(visitData);
      } catch (err) {
        console.error(err);
        setError("Failed to load patient details");
      } finally {
        setLoading(false);
      }
    };

    loadPatientDetails();
  }, [id]);

  if (loading) return <p>Loading patient details…</p>;
  if (error) return <p style={{ color: "red" }}>{error}</p>;

  return (
    <section className="patient">
      {/* Header */}
      <div className="patient-header">
        <div className="patient-header-text">
          <h1>{patient.patientName}</h1>
          <p>
            {patient.patientGender} • {patient.patientAge} years 
          </p>
          <p className="patient-meta">
            📞 {patient.patientPhone} &nbsp;&nbsp; 🩺 {patient.patientIssue} &nbsp;&nbsp;
         
            {patient.patientHeight}cm &nbsp;&nbsp;  {patient.patientWeight}kg
          </p>
        </div>

        <button
          className="patient-primary-btn"
          onClick={() => navigate(`/patients/${id}/visits/create`)}
        >
          + Add Visit
        </button>
      </div>

      {/* Visits */}
      <h3>Visits</h3>

      {visits.length === 0 ? (
        <p>No visits found for this patient.</p>
      ) : (
        <ul className="patient-grid">
          {visits.map((visit) => (
            <li key={visit.id} className="patient-card">
              <p className="patient-meta">
                Date: {new Date(visit.visitDate).toLocaleString()}
              </p>
            <p>{visit.treatmentNotes || "No notes provided"}</p>

            </li>
          ))}
        </ul>
      )}
    </section>
  );
}

export default PatientDetail;
