import api from './axios'
const receiptApi={
    
    createReceipt:async(receiptPayload)=>{
        const response=await api.post(`/receipts`,receiptPayload);
        return response.data
    },
  downloadReceiptPdf: async (receiptId) => {
    const response = await api.get(`/receipts/${receiptId}/pdf`, {
      responseType: "blob",   // 🔴 THIS IS CRITICAL
    });
    return response.data;     // actual PDF blob
  },
     getReceiptById:async(id)=>{
        const response=await api.get(`/receipts/${id}`);
        return response.data;
    },
    getReceiptsByPatient:async(patientId)=>{
        const response=await api.get(`/patients/${patientId}/receipts`)
        return response.data;
    },
     getReceiptByVisit: async (visitId) => {
    const response = await api.get(`/visits/${visitId}/receipt`);
    return response.data;
  },
    generateReceiptPdf: async (receiptId) => {
    const response = await api.get(`/receipts/${receiptId}/pdf`);
    return response.data; // string message for now
  },
  getAllReceipts: async () => {
  const response = await api.get("/receipts");
  return response.data;
},
updateReceipt: async (receiptId, updatePayload) => {
  const response = await api.patch(
    `/receipts/${receiptId}`,
    updatePayload
  );
  return response.data;
}

     
}
export default receiptApi;