package control;

import java.util.Scanner;
import service.AdminService;
import service.JobPosterService;
import service.JobSeekerService;
import utility.ConnectionUtility;

public class Main {

	public static void main(String[] args) throws Exception {
		
		// create ConnectionUtility object
		ConnectionUtility connectionUtility = new ConnectionUtility();
		connectionUtility.connect("jdbc:mysql://localhost:3306/internal_job_portal", "root", "root");
		
		// AdminService object
		AdminService adminService = new AdminService(connectionUtility);
		
		// jobPOster service object
		JobPosterService jobPosterService = new JobPosterService(connectionUtility);
		
		// jobSeeker service object
		JobSeekerService jobSeekerService = new JobSeekerService(connectionUtility);
		
		String name, email, password,confirmPassword;
		String companyName, jobCategory, jobRole, location;
		float experience;
		int salary;
		boolean msg;
		
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("Enter choice  1. Admin  2. Job Poster  3. Job Seeker  4. exit");
			int option = Integer.parseInt(sc.nextLine());
			switch(option) {
				case 1:
					
					while(true) {
						System.out.println("Enter choice for Admin operation ");
						System.out.println("1. Signup  2. Signin  3. displayUsers  4. deleteUser  5. displayJobs  6. deleteJobs  7. Exit");
						int choice = Integer.parseInt(sc.nextLine());
						switch (choice) {
							case 1:								
								System.out.println("Enter name :");
								name = sc.nextLine();
								System.out.println("Enter email :");
								email = sc.nextLine();
								System.out.println("Enter password :");
								password = sc.nextLine();
								System.out.println("Enter confirm password :");
								confirmPassword = sc.nextLine();
								msg = adminService.signup(name, email, password, confirmPassword);
								break;								
							case 2:								
								System.out.println("Enter email :");
								email = sc.nextLine();
								System.out.println("Enter password :");
								password = sc.nextLine();
								msg = adminService.signin(email, password);
								break;								
							case 3:								
								adminService.displayUsers();
								break;								
							case 4:								
								adminService.displayUsers();
								System.out.println("Enter the user id to delete :");
								int userId = Integer.parseInt(sc.nextLine());
								adminService.deleteUser(userId);
								break;								
							case 5:								
								adminService.displayJobs();
								break;								
							case 6:								
								adminService.displayJobs();
								System.out.println("Enter the job id to delete :");
								int jobId = Integer.parseInt(sc.nextLine());
								adminService.deleteJobs(jobId);
								break;								
							case 7:								
								System.exit(0);		
							default :
								System.out.println("Invalid choice... !");
						}
					}
//					break;
					
				case 2:
					
					while(true) {
						System.out.println("Enter choice for Job Poster operation ");
						System.out.println("1. Signup  2. Signin  3. displayJobs  4. postJobs  5. displayAppliedJobs   6. Exit");
						int choice = Integer.parseInt(sc.nextLine());
						switch (choice) {
							case 1:
								
								System.out.println("Enter name :");
								name = sc.nextLine();
								System.out.println("Enter email :");
								email = sc.nextLine();
								System.out.println("Enter password :");
								password = sc.nextLine();
								System.out.println("Enter confirm password :");
								confirmPassword = sc.nextLine();
								msg = jobPosterService.signup(name, email, password, confirmPassword);
								break;
								
							case 2:
								
								System.out.println("Enter email :");
								email = sc.nextLine();
								System.out.println("Enter password :");
								password = sc.nextLine();
								msg = jobPosterService.signin(email, password);
								break;
								
							case 3:
								
								jobPosterService.displayJobs();
								break;
								
							case 4:
								
								System.out.println("Enter company name :");
								companyName = sc.nextLine();
								System.out.println("Enter job category :");
								jobCategory = sc.nextLine();
								System.out.println("Enter job role :");
								jobRole = sc.nextLine();
								System.out.println("Enter experience :");
								experience = Float.parseFloat(sc.nextLine());
								System.out.println("Enter location :");
								location = sc.nextLine();
								System.out.println("Enter salary :");
								salary = Integer.parseInt(sc.nextLine());
								msg =  jobPosterService.postJobs(companyName, jobCategory, jobRole, experience, location, salary);
								break;
								
							case 5:
								
								jobPosterService.displayAppliedJobs();
								break;
								
							case 6:
								
								System.exit(0);
								
							default :
								
								System.out.println("Invalid choice... !");
						}
					}
//					break;
					
				case 3:
					
					while(true) {
						System.out.println("Enter choice for Job Seeker operation ");
						System.out.println("1. Signup  2. Signin  3. displayJobs  4. appliedJobs  5. Exit");
						int choice = Integer.parseInt(sc.nextLine());
						switch (choice) {
							case 1:
								
								System.out.println("Enter name :");
								name = sc.nextLine();
								System.out.println("Enter email :");
								email = sc.nextLine();
								System.out.println("Enter password :");
								password = sc.nextLine();
								System.out.println("Enter confirm password :");
								confirmPassword = sc.nextLine();
								msg = jobSeekerService.signup(name, email, password, confirmPassword);
								break;
								
							case 2:
								
								System.out.println("Enter email :");
								email = sc.nextLine();
								System.out.println("Enter password :");
								password = sc.nextLine();
								msg = jobSeekerService.signin(email, password);
								break;
								
							case 3:
								
								jobSeekerService.displayJobs();
								break;
								
							case 4:
								
								jobSeekerService.displayJobs();
								System.out.println("Enter job id :");
								int jobId = Integer.parseInt(sc.nextLine());
								msg = jobSeekerService.appliedJobs(jobId);
								break;
								
							case 5:
								
								System.exit(0);
								
							default :
								
								System.out.println("Invalid choice... !");
						}
					}
//					break;
					
				case 4:
					
					System.exit(0);
					
				default:
					
					System.out.println("Invalid input of option... !");
			}
		}
	}

}
