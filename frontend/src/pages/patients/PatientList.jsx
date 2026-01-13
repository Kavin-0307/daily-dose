import patientApi from "../../api/patientApi";
import { useEffect,useState } from "react";
import { useNavigate } from "react-router-dom";

import "./patients.css"
function PatientList() {
  const[patients,setPatients]=useState([]);
  const navigate = useNavigate();
const [search, setSearch] = useState("");
const filteredPatients = patients
  .filter(p =>
    p.patientName.toLowerCase().includes(search.toLowerCase())
  )
  .slice(0, 5); 

      useEffect(()=>{
        const loadPatients=async()=>{
        try{
         const data=await patientApi.getAllPatients();
          console.log("PATIENT DATA FROM API:", data); 
         setPatients(data);
        }
        catch(error){
          console.error("Failed to get patients ",error);
        }
      }
      loadPatients();
    },[]);
  return (
    <section className="patient">
      <div className="patient-header">
  <div className="patient-header-text">
    <h1>Patients</h1>
    <p>Manage and view recent patient records</p>
  </div>

  <button
    className="patient-primary-btn"
    onClick={() => navigate("/patients/create")}
  >
    + Create Patient
  </button>
</div>
<div className="patient-search">
  <input
    type="text"
    placeholder="Search patients by name"
    value={search}
    onChange={(e) => setSearch(e.target.value)}
  />
</div><ul className="patient-grid">
  {filteredPatients.map((patient) => (
    <li
      key={patient.id}
      className="patient-card"
      onClick={() => navigate(`/patients/${patient.id}`)}
    >
      <h3>{patient.patientName}</h3>

      <p className="patient-meta">
        Age: {patient.patientAge} • {patient.patientGender}
      </p>

      <p className="patient-meta">
        Phone: {patient.patientPhone}
      </p>
    </li>
  ))}
</ul>



    </section>
  );
}

export default PatientList;
