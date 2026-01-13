import api from './axios'
const staffApi={
    getAllStaff:async()=>{
        const response =await api.get(`/staff`);
        return response.data
    },
    createStaff:async(staffPayload)=>{
        const response=await api.post(`/staff`,staffPayload);
        return response.data
    },
     getStaffById:async(id)=>{
        const response=await api.get(`/staff/${id}`);
        return response.data;
    },
     deactivateStaff: async (id) => {
    const response = await api.put(`/staff/${id}/deactivate`);
    return response.data;
  }
}
export default staffApi;