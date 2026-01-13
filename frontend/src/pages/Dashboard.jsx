
import { Link } from 'react-router-dom';
/*import { fadeIn } from '../fade';
import { useRef } from 'react';*/
import { useNavigate } from "react-router-dom";
import ddLogo from './DD_logo.png';
import "./Dashboard.css"


function Dashboard(){
   // const receiptsBtnRef = useRef(null);
 const navigate = useNavigate();
    /*const handleFadeClick = () => {
        if (receiptsBtnRef.current) {
            fadeIn(receiptsBtnRef.current, 1000);
        }
    };*/
    
    return(

        <section className="dashboard" >
          <div className="dashboard-header">
            <img src={ddLogo} className="logo" alt="Daily Dose logo" />

        
        <div>
          <h2>DailyDose</h2>
          <p>Welcome, staff :)</p>
        </div>
      </div>    
      <div className="dashboard-buttons">
        <button
        className="dashboard-button"
        onClick={() => navigate("/patients")}
        
      >
        Patient Management
        ➢Create Patients
        ➢Access Patients
      </button>

       
        <button
        className="dashboard-button"
       
        onClick={() => navigate("/receipts")}
      >
        Receipts<br/>
        ➢Create a new Receipt<br/>
        ➢Create pdf of a existing receipts
      </button>
        
      <button
        className="dashboard-button"
        
        onClick={() => navigate("/staff")}
      >
        Staff<br/>
        ➢Manage staff
        <br/>➢Access the patients by staff
      </button>
   <button
  className="dashboard-button"
  onClick={() => navigate("/appointments")}
>
  Appointments<br/>
  ➢Create a new Appointment<br/>
  ➢View All Appointments
</button>

      </div>
        </section>
    );
}
export default Dashboard;
