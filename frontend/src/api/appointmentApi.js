import api from "./axios";

const appointmentApi = {
  createAppointment: async (payload) => {
    const res = await api.post(`/appointments`, payload);
    return res.data;
  },
  getAppointmentsByStaff: async (staffId) => {
    const res = await api.get(`/staff/${staffId}/appointments`);
    return res.data;
  },
  getAppointmentsByPatient: async (patientId) => {
    const res = await api.get(`/patients/${patientId}/appointments`);
    return res.data;
  },

  cancelAppointment: async (id) => {
    await api.put(`/appointments/${id}/cancel`);
  },
  getAllAppointments: async () => {
  console.log("TOKEN:", localStorage.getItem("token"));
  const res = await api.get("/appointments");
  return res.data;
},


};

export default appointmentApi;
