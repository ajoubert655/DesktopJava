/*Author: Andries Joubert
Threaded Project Term 3
Project Workshop 6(Java)
Group: Group 3
 */

package TravelDetailsDisplay;

//There is a single class for the columns we need from both tables.
public class CustomerBooking {
    private int CustomerId;
    private String CustFirstName;
    private String CustLastName;
    private String CustCity;
    private String CustCountry;
    private int BookingId;
    private String BookingDate;
    private String TripTypeId;
    private int PackageId;

    public CustomerBooking(int customerId, String custFirstName, String custLastName, String custCity, String custCountry, int bookingId, String bookingDate, String tripTypeId, int packageId) {
        CustomerId = customerId;
        CustFirstName = custFirstName;
        CustLastName = custLastName;
        CustCity = custCity;
        CustCountry = custCountry;
        BookingId = bookingId;
        BookingDate = bookingDate;
        TripTypeId = tripTypeId;
        PackageId = packageId;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public String getCustFirstName() {
        return CustFirstName;
    }

    public void setCustFirstName(String custFirstName) {
        CustFirstName = custFirstName;
    }

    public String getCustLastName() {
        return CustLastName;
    }

    public void setCustLastName(String custLastName) {
        CustLastName = custLastName;
    }

    public String getCustCity() {
        return CustCity;
    }

    public void setCustCity(String custCity) {
        CustCity = custCity;
    }

    public String getCustCountry() {
        return CustCountry;
    }

    public void setCustCountry(String custCountry) {
        CustCountry = custCountry;
    }

    public int getBookingId() {
        return BookingId;
    }

    public void setBookingId(int bookingId) {
        BookingId = bookingId;
    }

    public String getBookingDate() {
        return BookingDate;
    }

    public void setBookingDate(String bookingDate) {
        BookingDate = bookingDate;
    }

    public String getTripTypeId() {
        return TripTypeId;
    }

    public void setTripTypeId(String tripTypeId) {
        TripTypeId = tripTypeId;
    }

    public int getPackageId() {
        return PackageId;
    }

    public void setPackageId(int packageId) {
        PackageId = packageId;
    }

    @Override
    public String toString() {
        return  "Customer ID: " + CustomerId + " " + "Booking ID: " + BookingId;
    }
}
