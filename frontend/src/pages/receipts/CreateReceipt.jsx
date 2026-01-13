import { useEffect, useState } from "react";
import {  useNavigate } from "react-router-dom";
import receiptApi from "../../api/receiptApi.js";
import visitApi from "../../api/visitApi.js"
import patientApi from "../../api/patientApi";
import "../receipts/receipts.css"
function CreateReceipt() {
 const navigate = useNavigate();
  const[receiptAmount,setRecieptAmount]=useState("");
   const [receiptDescription,setReceiptDescription]=useState("");
 
  const [receiptPaymentMethod,setReceiptPaymentMethod]=useState("");
const [patients, setPatients] = useState([]);
const [visits, setVisits] = useState([]);
const [selectedPatientId, setSelectedPatientId] = useState("");
const [visitId, setVisitId] = useState("");

  const[paid,setPaid]=useState(false);
  const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    useEffect(() => {
  const loadPatients = async () => {
    try {
      const data = await patientApi.getAllPatients();
      setPatients(data);
    } catch (err) {
      console.error("Failed to load patients", err);
    }
  };
  loadPatients();
}, []);
useEffect(() => {
  if (!selectedPatientId || Number.isNaN(selectedPatientId)) {
    setVisits([]);
    setVisitId(null);
    return;
  }

  const loadVisits = async () => {
    try {
      const data = await visitApi.getVisitsByPatient(selectedPatientId);
      setVisits(data);
    } catch (err) {
      console.error("Failed to load visits", err);
    }
  };

  loadVisits();
}, [selectedPatientId]);

const handleSubmit = async (e) => {
  e.preventDefault();
  try {
    setLoading(true);

    await receiptApi.createReceipt({
      patientId:Number(selectedPatientId),
      staffId:1,
      visitId:visitId||null,
      receiptAmount:Number(receiptAmount),
      receiptDescription:receiptDescription,
      receiptPaymentMethod:receiptPaymentMethod,
      paid:paid
    });
navigate("/receipts");

  } catch (err) {
    console.error(err);
    setError("Failed to create patient");
  } finally {
    setLoading(false);
  }

};

  return ( <section className="receipt">
      <div className="receipt-header" style={{border:'2px solid black'}}>
        <div className="receipt-header-text">
          <h1>Create Receipt</h1>
        </div>
      </div>
      {error && <p style={{ color: "red" }}>{error}</p>}
<form onSubmit={handleSubmit}>
  <div
    className="receipt-card receipt-form"
    style={{
      
      marginTop: "24px",
      border:'2px solid black'
    }}
  >
   <select
  value={selectedPatientId ?? ""}
  onChange={(e) => {
    const value = parseInt(e.target.value, 10);
    setSelectedPatientId(Number.isNaN(value) ? null : value);
  }}
  required
>
  <option value="">Select patient</option>
  {patients.map((p) => (
    <option
      key={p.patientId ?? p.id}
      value={p.patientId ?? p.id}
    >
      {p.patientName}
    </option>
  ))}
</select>

<select
  value={visitId}
  onChange={(e) => setVisitId(e.target.value)}
  disabled={!selectedPatientId}
>
  {!selectedPatientId && (
    <option value="">
      Select patient first
    </option>
  )}

  {selectedPatientId && (
    <option value="">
      Select visit (optional)
    </option>
  )}
{visits.map((v) => (
  <option key={`visit-${v.visitId}`} value={v.visitId}>
    {new Date(v.visitDate).toLocaleDateString()}
  </option>
))}

</select>


  
     <input
  type="number"
  value={receiptAmount}
  onChange={(e) => setRecieptAmount(e.target.value)}
  placeholder="Enter Receipt Amount"
  required
/>
<textarea
  value={receiptDescription}
  onChange={(e) => setReceiptDescription(e.target.value)}
  rows={4}
  placeholder="Enter description / charges"
  required
/><div className="receipt-form-row">
  <select
    value={receiptPaymentMethod}
    onChange={(e) => setReceiptPaymentMethod(e.target.value)}
    required
  >
    <option value="">Select payment method</option>
    <option value="CASH">Cash</option>
    <option value="UPI">UPI</option>
    <option value="CARD">Card</option>
  </select>

  <label style={{ display: "flex", alignItems: "center", gap: "6px" }}>
    <input
      type="checkbox"
      checked={paid}
      onChange={(e) => setPaid(e.target.checked)}
    />
    Paid
  </label>
</div>
    </div>

    <div style={{ display: "flex", gap: "12px" }}>
      <button
        type="submit"
        className="receipt-primary-btn"
        disabled={loading}
        
      >
        {loading ? "Saving..." : "Create Receipt"}
      </button>

      <button
        type="button"
        onClick={() => navigate(`/receipts`)}
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

export default CreateReceipt;
