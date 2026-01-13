import api from './axios'
const visitApi={
    getAllVisits:async()=>{
        const response =await api.get(`/visits`);
        return response.data
    },
    createVisit:async(visitPayload)=>{
        const response=await api.post(`/visits`,visitPayload);
        return response.data
    },
     getVisitById:async(id)=>{
        const response=await api.get(`/visits/${id}`);
        return response.data;
    },
    getVisitsByPatient:async(patientId)=>{
        const response=await api.get(`/patients/${patientId}/visits`)
        return response.data;
    },
   // visitApi.js
getVisitsByStaff: async (staffId) => {
  const response = await api.get(`/visits/staff/${staffId}`);
  return response.data;
}
,
     updateVisit: async (id, visitPayload) => {
    const response = await api.put(`/visits/${id}`, visitPayload);
    return response.data;
  }
}
export default visitApi;