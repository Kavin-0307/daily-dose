import api from "./axios";

const appointmentApi = {
  createAppointment: async (payload) => {
    const res = await api.post(`/appointments`, payload);
    return res.data;
  },

  // ✅ FIXED
  getAppointmentsByStaff: async (staffId) => {
    const res = await api.get(`/staff/${staffId}/appointments`);
    return res.data;
  },

  // ✅ FIXED
  getAppointmentsByPatient: async (patientId) => {
    const res = await api.get(`/patients/${patientId}/appointments`);
    return res.data;
  },

  cancelAppointment: async (id) => {
    await api.put(`/appointments/${id}/cancel`);
  },
};

export default appointmentApi;
