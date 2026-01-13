import { Routes, Route } from "react-router-dom";
import ReceiptDetails from "./pages/receipts/ReceiptDetails";

import LoginPage from "./pages/LoginPage";
import Dashboard from "./pages/Dashboard";
import PatientList from "./pages/patients/PatientList";
import CreatePatient from "./pages/patients/CreatePatient";
import ProtectedRoute from "./auth/ProtectedRoute";
import ReceiptList from "./pages/receipts/ReceiptList";
import StaffList from "./pages/staff/StaffList";
import PatientDetail from "./pages/patients/PatientDetail";
import CreateVisit from "./pages/visits/CreateVisit"
import CreateReceipts from "./pages/receipts/CreateReceipt";
import CreateStaff from "./pages/staff/CreateStaff";
import StaffPatients from "./pages/staff/StaffPatients";
import CreateAppointment from "./pages/appointments/CreateAppointment";
import StaffAppointments from "./pages/appointments/StaffAppointments";
function AppRoutes() {
  return (
    <Routes>
      <Route path="/login" element={<LoginPage />} />

      <Route
        path="/"
        element={
          <ProtectedRoute>
            <Dashboard />
          </ProtectedRoute>
        }
      />
      <Route path="/staff/:staffId/patients" element={<StaffPatients />} />
        <Route path="/appointments/create" element={<CreateAppointment />} />
<Route path="/staff/:staffId/appointments" element={<StaffAppointments />} />

      <Route
  path="/staff/create"
  element={
    <ProtectedRoute>
      <CreateStaff />
    </ProtectedRoute>
  }
/>

      <Route
        path="/patients"
        element={
          <ProtectedRoute>
            <PatientList />
          </ProtectedRoute>
        }
      />
      <Route
  path="/receipts/:id"
  element={
    <ProtectedRoute>
      <ReceiptDetails />
    </ProtectedRoute>
  }
/>

  <Route
  path="/patients/:id"
  element={
    <ProtectedRoute>
      <PatientDetail />
    </ProtectedRoute>
  }
/>
<Route
  path="/patients/:id/visits/create"
  element={
    <ProtectedRoute>
      <CreateVisit />
    </ProtectedRoute>
  }
/>

      <Route
        path="/patients/create"
        element={
          <ProtectedRoute>
            <CreatePatient />
          </ProtectedRoute>
        }
      />
      
      <Route
        path="/receipts/create"
        element={
          <ProtectedRoute>
            <CreateReceipts />
          </ProtectedRoute>
        }
      />
      <Route path="/receipts" element={<ProtectedRoute><ReceiptList /></ProtectedRoute>} />
        <Route path="/staff" element={<ProtectedRoute><StaffList/></ProtectedRoute>} />

    </Routes>
  );
}

export default AppRoutes;
