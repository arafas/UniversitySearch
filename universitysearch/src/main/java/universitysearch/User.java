package universitysearch;

public class User {
	   private int id;
	   private String email, password, firstName, lastName, emailHash;   
	   private int isProf, isAdmin, emailVer;

	   public User() {}
	   public User(String email, String pass, String fName, String lName, int isPr, int isAd, int emailVer, String emailHash) {
		  this.email = email;
		  this.password = pass;
	      this.firstName = fName;
	      this.lastName = lName;
	      this.isProf = isPr;
	      this.isAdmin = isAd;
	      this.emailVer = emailVer;
	      this.emailHash = emailHash;
	   }
	   public int getId() {
	      return id;
	   }
	   public void setId(int id) {
	      this.id = id;
	   }
	   
	   public String getEmail() {
		      return email;
	   }
	   public void setEmail(String email) {
		      this.email = email;
	   }
	   
	   public String getEmailHash() {
		      return emailHash;
	   }
	   public void setEmailHash(String emailHash) {
		      this.emailHash = emailHash;
	   }
	   
	   public String getPassword() {
		      return password;
	   }
	   public void setPassword(String pass) {
		      this.password = pass;
	   }
	   
	   public String getFirstName() {
	      return firstName;
	   }
	   public void setFirstName( String fName) {
	      this.firstName = fName;
	   }
	   
	   public String getLastName() {
	      return lastName;
	   }
	   public void setLastName( String lName ) {
	      this.lastName = lName;
	   }

	   public int getIsProf() {
		      return isProf;
	   }
	   public void setIsProf(int IsProf) {
		      this.isProf = IsProf;
	   }
	   
	   public int getIsAdmin() {
		      return isAdmin;
	   }
	   public void setIsAdmin(int isAdmin) {
		      this.isAdmin = isAdmin;
	   }
	   
	   public int getEmailVer() {
		      return emailVer;
	   }
	   public void setEmailVer(int emailVer) {
		      this.emailVer = emailVer;
	   }
}
