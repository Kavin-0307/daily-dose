import api from './axios';
const patientApi={
    getAllPatients:async()=>{
        const response=await api.get(`/patients`);
        return response.data;
    },
    getPatientById:async(patientId)=>{
        const response=await api.get(`/patients/${patientId}`);
        return response.data;
    },
    createPatient:async(patientPayload)=>{
        const response=await api.post(`/patients`,patientPayload);
        return response.data;
    },
    getPatientsByStaff: async (staffId) => {
  const response = await api.get(`/staff/${staffId}/patients`);
  return response.data;
}

}
export default patientApi;