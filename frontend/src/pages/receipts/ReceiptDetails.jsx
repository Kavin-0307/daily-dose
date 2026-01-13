import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import receiptApi from "../../api/receiptApi";
import "./receipts.css";

function ReceiptDetails() {
  const { id } = useParams();
  const navigate = useNavigate();
    const PAYMENT_METHODS = ["CASH", "CARD", "UPI"];

  const [receipt, setReceipt] = useState(null);
  const [description, setDescription] = useState("");
  const [paymentMethod, setPaymentMethod] = useState("");
  const [markPaid, setMarkPaid] = useState(false);

  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    const loadReceipt = async () => {
      try {
        setLoading(true);
        const data = await receiptApi.getReceiptById(id);
        setReceipt(data);
        setDescription(data.receiptDescription || "");
        setPaymentMethod(data.receiptPaymentMethod || "");
        setMarkPaid(data.receiptPaid);
      } catch (err) {
        console.error(err);
        setError("Failed to load receipt");
      } finally {
        setLoading(false);
      }
    };

    loadReceipt();
  }, [id]);

  const handleSave = async () => {
    try {
      setSaving(true);

      const payload = {
        receiptDescription: description,
        receiptPaymentMethod: paymentMethod,
      };

      // Only send receiptPaid if transitioning false -> true
      if (!receipt.receiptPaid && markPaid) {
        payload.receiptPaid = true;
      }

      const updated = await receiptApi.updateReceipt(id, payload);
      setReceipt(updated);
      alert("Receipt updated successfully");
    } catch (err) {
      console.error(err);
      alert("Failed to update receipt");
    } finally {
      setSaving(false);
    }
  };

  const handleDownloadPdf = async () => {
    try {
      const blob = await receiptApi.downloadReceiptPdf(receipt.receiptId);
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement("a");
      a.href = url;
      a.download = `${receipt.receiptNumber}.pdf`;
      document.body.appendChild(a);
      a.click();
      a.remove();
      window.URL.revokeObjectURL(url);
    } catch (err) {
      console.error(err);
      alert("Failed to download PDF");
    }
  };

  if (loading) return <p>Loading receipt...</p>;
  if (error) return <p style={{ color: "red" }}>{error}</p>;
  if (!receipt) return null;

  return (
    <section className="receipt">
      <div className="receipt-header">
        <h1>Receipt Details</h1>
        <button
  className="receipt-secondary-btn"
  onClick={() => navigate("/receipts")}
>
  ← Back to Receipts
</button>

      </div>
        <div className="receipt-card">
  <p><strong>Receipt Number:</strong> {receipt.receiptNumber}</p>
  <p><strong>Patient:</strong> {receipt.patientName}</p>
  <p><strong>Amount:</strong> ₹{receipt.amount}</p>
  <p>
    <strong>Created:</strong>{" "}
    {new Date(receipt.createdAt).toLocaleString()}
  </p>

  <hr style={{ margin: "16px 0" }} />

  <div style={{ marginBottom: "12px" }}>
    <label>
      <strong>Description</strong>
    </label>
    <textarea
      value={description}
      onChange={(e) => setDescription(e.target.value)}
      rows={3}
      style={{ width: "100%", marginTop: "6px" }}
      placeholder="Add or update receipt description"
    />
  </div>

  <select
  value={paymentMethod}
  onChange={(e) => setPaymentMethod(e.target.value)}
  style={{ width: "100%", marginTop: "6px", padding: "6px" }}
>
  <option value="">Select payment method</option>
  {PAYMENT_METHODS.map((method) => (
    <option key={method} value={method}>
      {method}
    </option>
  ))}
</select>


  <div style={{ marginBottom: "16px" }}>
    <label>
      <input
        type="checkbox"
        checked={markPaid}
        disabled={receipt.receiptPaid}
        onChange={(e) => setMarkPaid(e.target.checked)}
        style={{ marginRight: "6px" }}
      />
      <strong>Mark as Paid</strong>
    </label>
  </div>

  <button
    className="receipt-primary-btn"
    onClick={handleSave}
    disabled={saving}
  >
    {saving ? "Saving..." : "Save Changes"}
  </button>

  <button
    className="receipt-secondary-btn"
    onClick={handleDownloadPdf}
    style={{ marginLeft: "8px" }}
  >
    📄 Download PDF
  </button>
</div>

  
    </section>
  );
}

export default ReceiptDetails;
