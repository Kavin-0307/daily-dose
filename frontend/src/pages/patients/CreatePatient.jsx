//Adhaar,DOB,weight,email,phone numb,name,issue,gender,height,age,address
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import patientApi from "../../api/patientApi";
function CreatePatient() {
  const navigate = useNavigate();
  const[adhaar,setAdhaar]=useState("");
  const[dob,setDob]=useState("");
   const [height,setHeight]=useState("");
  const [weight,setWeight]=useState("");
  const [email,setEmail]=useState("");
  const[phoneNumber,setPhoneNumber]=useState("");
  const [name,setName]=useState("");
 const [issue,setIssue]=useState("");
 const[age,setAge]=useState("");
 const [gender,setGender]=useState("");
 const [address,setAddress]=useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const handleSubmit = async (e) => {
  e.preventDefault();

  try {
    setLoading(true);

    await patientApi.createPatient({
      
      patientAdhaar:adhaar,
      patientDob:dob,
      patientEmail:email,
      patientGender:gender,
      patientAge:Number(age),
      patientIssue:issue,
      patientHeight:Number(height),
       patientWeight:Number(weight),
       patientPhone:phoneNumber,
     patientAddress:address,
      patientName:name,
      
      
    });
navigate("/patients");

  } catch (err) {
    console.error(err);
    setError("Failed to create patient");
  } finally {
    setLoading(false);
  }

};

  return (
    <section className="patient">
      <div className="patient-header" style={{border:'2px solid black'}}>
        <div className="patient-header-text">
          <h1>Create Patient</h1>
          <p>Record clinical notes for this patient</p>
        </div>
      </div>
      {error && <p style={{ color: "red" }}>{error}</p>}
<form onSubmit={handleSubmit}>
  <div
    className="patient-card"
    style={{
      
      marginTop: "24px",
      border:'2px solid black'
    }}
  >
  
      <textarea
        value={address}
        onChange={(e) => setAddress(e.target.value)}
        rows={6}
        placeholder="Enter Patient Address"
        required
        style={{
          width: "50%",
          padding: "14px",
          borderRadius: "12px",
          boxSizing: "border-box"
,
          border: "1px solid #d1d5db",
          fontFamily: "inherit",
          fontSize: "14px",
          resize: "vertical"
        }}
        
      />
       <textarea
        value={phoneNumber}
        onChange={(e) => setPhoneNumber(e.target.value)}
        rows={6}
        placeholder="Enter Patient Phone Number"
        required
        style={{
          width: "50%",
          padding: "14px",
          borderRadius: "12px",
          boxSizing: "border-box"
,
          border: "1px solid #d1d5db",
          fontFamily: "inherit",
          fontSize: "14px",
          resize: "vertical"
        }}
        
      />
      
       <textarea
        value={name}
        onChange={(e) => setName(e.target.value)}
        rows={6}
       placeholder="Enter Patient Name"
        required
        style={{
          width: "50%", 
          padding: "14px",
          borderRadius: "12px",
          boxSizing: "border-box"
,
          border: "1px solid #d1d5db",
          fontFamily: "inherit",
          fontSize: "14px",
          resize: "vertical"
        }}
        
      />
       <textarea
        value={adhaar}
        onChange={(e) => setAdhaar(e.target.value)}
        rows={6}
       placeholder="Enter Patient Adhaar"
        required
        style={{
          width: "50%",
          padding: "14px",
          borderRadius: "12px",
          boxSizing: "border-box"
,
          border: "1px solid #d1d5db",
          fontFamily: "inherit",
          fontSize: "14px",
          resize: "vertical"
        }}
        
      />
         <textarea
        value={dob}
        onChange={(e) => setDob(e.target.value)}
        rows={6}
       placeholder="Enter Patient Date Of Birth"
        required
        style={{
          width: "50%",
          padding: "14px",
          borderRadius: "12px",
          boxSizing: "border-box"
,
          border: "1px solid #d1d5db",
          fontFamily: "inherit",
          fontSize: "14px",
          resize: "vertical"
        }}
        
      />
         <textarea
        value={height}
        onChange={(e) => setHeight(e.target.value)}
        rows={6}
       placeholder="Enter Patient Height"
        required
        style={{
          width: "50%",
          padding: "14px",
          borderRadius: "12px",
          boxSizing: "border-box"
,
          border: "1px solid #d1d5db",
          fontFamily: "inherit",
          fontSize: "14px",
          resize: "vertical"
        }}
        
      />
         <textarea
        value={weight}
        onChange={(e) => setWeight(e.target.value)}
        rows={6}
       placeholder="Enter Patient Weight"
        required
        style={{
          width: "50%",
          padding: "14px",
          borderRadius: "12px",
          boxSizing: "border-box"
,
          border: "1px solid #d1d5db",
          fontFamily: "inherit",
          fontSize: "14px",
          resize: "vertical"
        }}
        
      />
         <textarea
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        rows={6}
       placeholder="Enter Patient Email"
        required
        style={{
          width: "50%",
          padding: "14px",
          borderRadius: "12px",
          boxSizing: "border-box"
,
          border: "1px solid #d1d5db",
          fontFamily: "inherit",
          fontSize: "14px",
          resize: "vertical"
        }}
        
      />
         <textarea
        value={issue}
        onChange={(e) => setIssue(e.target.value)}
        rows={6}
       placeholder="Enter Patient Issue"
        required
        style={{
          width: "50%",          // ✅ FIX
          padding: "14px",
          borderRadius: "12px",
          boxSizing: "border-box"
,
          border: "1px solid #d1d5db",
          fontFamily: "inherit",
          fontSize: "14px",
          resize: "vertical"
        }}
        
      />
         <textarea
        value={age}
        onChange={(e) => setAge(e.target.value)}
        rows={6}
       placeholder="Enter Patient Age"
        required
        style={{
          width: "50%",          // ✅ FIX
          padding: "14px",
          borderRadius: "12px",
          boxSizing: "border-box"
,
          border: "1px solid #d1d5db",
          fontFamily: "inherit",
          fontSize: "14px",
          resize: "vertical"
        }}
        
      />
         <textarea
        value={gender}
        onChange={(e) => setGender(e.target.value)}
        rows={6}
       placeholder="Enter Patient Gender"
        required
        style={{
          width: "50%",          // ✅ FIX
          padding: "14px",
          borderRadius: "12px",
          boxSizing: "border-box"
,
          border: "1px solid #d1d5db",
          fontFamily: "inherit",
          fontSize: "14px",
          resize: "vertical"
        }}
        
      />
    </div>

    <div style={{ display: "flex", gap: "12px" }}>
      <button
        type="submit"
        className="patient-primary-btn"
        disabled={loading}
        
      >
        {loading ? "Saving..." : "Create Patient"}
      </button>

      <button
        type="button"
        onClick={() => navigate(`/patients`)}
        style={{
          background: "transparent",
          border: "none",
          color: "#6b7280",
          cursor: "pointer"
        }}
      >
        Cancel
      </button>
    </div>
  
</form>

    </section>
  );
}

export default CreatePatient;
