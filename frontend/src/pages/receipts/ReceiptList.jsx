import { useEffect, useState } from "react";
import {  useNavigate } from "react-router-dom";
import receiptApi from "../../api/receiptApi.js"
import "../receipts/receipts.css"
function ReceiptList() {
  const navigate = useNavigate();
 const [receipts, setReceipts] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const loadReceipts = async () => {
      try {
        setLoading(true);

        const receiptData = await receiptApi.getAllReceipts();
        setReceipts(receiptData);
        console.log("Receipts response:", receiptData);

        } catch (err) {
        console.error(err);
        setError("Failed to load receipts");
      } finally {
        setLoading(false);

      }
      
    };

    loadReceipts();
    
  }, []);
  const filteredReceipts = receipts.filter((receipt) => {
  const term = searchTerm.toLowerCase();

  const patientMatch = receipt.patientName
    ?.toLowerCase()
    .includes(term);

  const receiptNumberMatch = receipt.receiptNumber
    ?.toString()
    .toLowerCase()
    .includes(term);

  return patientMatch || receiptNumberMatch;
});

  if (loading) return <p>Loading receipts…</p>;
  if (error) return <p style={{ color: "red" }}>{error}</p>;

  const handleDownloadPdf = async (receiptId, receiptNumber) => {
  try {
    const blob = await receiptApi.downloadReceiptPdf(receiptId);

    const url = window.URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = `${receiptNumber}.pdf`;
    document.body.appendChild(a);
    a.click();
    a.remove();
    window.URL.revokeObjectURL(url);
  } catch (err) {
    console.error("Failed to download PDF", err);
    alert("Failed to download receipt PDF");
  }
};

  return (
    <section className="receipt">
      {/* Header */}
      <div className="receipt-header">
        
        <h1>Receipts</h1>
        

        <button
          className="receipt-primary-btn"
          onClick={() => navigate(`/receipts/create`)}
        >
          + Create Receipt
        </button>
         <button
  className="receipt-secondary-btn"
  onClick={() => navigate("/receipts")}
>
  ← Back to Receipts
</button>
      </div>
    
      {/* Visits */}
      <h3>Receipts</h3>
    <input
        type="text"
        placeholder="Search by patient name or reciept number"
        value={searchTerm}
        className="receipt-search"
        onChange={(e) => setSearchTerm(e.target.value)}
        style={{ marginBottom: "16px", padding: "8px" }}
      />
      {filteredReceipts.length === 0 ? (
        <p>No receipts found for this patient.</p>
      ) : (
        <ul className="receipt-grid">
          {filteredReceipts.map((receipt) => (
            <li key={receipt.receiptId} className="receipt-card">
              <p><strong>Receipt Number:</strong>{receipt.receiptNumber}</p>
              <p><strong>Patient:</strong> {receipt.patientName}</p>
              <p><strong>Amount:</strong> ₹{receipt.amount}</p>
              <p><strong>Method:</strong> {receipt.receiptPaymentMethod}</p>
              <p><strong>Status:</strong> {receipt.paid ? "Paid" : "Unpaid"}</p>
              <button
        className="receipt-secondary-btn"
        onClick={() =>
          handleDownloadPdf(
            receipt.receiptId,
            receipt.receiptNumber
          )
        }
      >
        📄 Download PDF
      </button>
      <button
  className="receipt-secondary-btn"
  onClick={() => navigate(`/receipts/${receipt.receiptId}`)}
>
  View / Edit
</button>

            </li>
          ))}
        </ul>
      )}
    </section>
  );
}

export default ReceiptList;
