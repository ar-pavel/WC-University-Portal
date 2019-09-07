# WC University Management System

  WC University is an Imginary University and this project is to maintain this university.

### Mudules:
  1.    Student View
    1.1 View Registered courses by him/her
    1.2 View current CGPA, number of credits completed
    1.3 Apply for convocation if eligible (depends on his/her program’s minimum credits for graduation criteria). Otherwise the option will be disabled
    1.4 Make payment for convocation (Selecting gateway and placing TrxID). This option will be disabled before applying. Also one can make payment only once.

  2.    Coordinator View
    2.1 View students registered in program he is coordinating
    2.2 View their grades and credits completed
    2.3 Select students and register them into different courses offered in that particular program.
  
  3.    Admission Officer View
    3.1 View students registered in all the programs
    3.2 Add students in different programs. Only into one of the programs created in the system, a student can be admitted. 
    3.3 Create initial login credentials for the students
  
  4.    Deputy Registrar(Academic) VIew
    4.1 View all the programs and courses offered in the university
    4.2 Create program
    4.3 Create courses. Course needs to be offered in any one of the created program. A program combo box makes it sure.
    
  5.    Deputy Registrar(Human Resource) VIew
    5.1 View all the employees in all the departments
    5.2 Add employee into any department and create initial credentials.



FrontEnd Link: 
https://wcu-portal.herokuapp.com/

Microservices links:

http://wcu-discovery-server.herokuapp.com/
https://wcu-gateway-api.herokuapp.com/
https://wcu-student-api.herokuapp.com/swagger-ui.html#
https://wcu-program-api.herokuapp.com/swagger-ui.html#
https://wcu-hr-api.herokuapp.com/swagger-ui.html#
https://wcu-auth-server.herokuapp.com/api/v1/auth
https://wcu-convocation-api.herokuapp.com/swagger-ui.html#
