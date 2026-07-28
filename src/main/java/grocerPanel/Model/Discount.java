package grocerPanel.Model;

public class Discount {

    private int discountID;
    private String code;
    private String discountType;
    private double discountValue;
    private boolean active;
    private String expirationDate;


    public Discount(int discountID, String code, String discountType,
                    double discountValue, boolean active, String expirationDate) {

        this.discountID = discountID;
        this.code = code;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.active = active;
        this.expirationDate = expirationDate;
    }


    public int getDiscountID() {
        return discountID;
    }

    public String getCode() {
        return code;
    }

    public String getDiscountType() {
        return discountType;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public boolean getActive() {
        return active;
    }

    public String getExpirationDate() {
        return expirationDate;
    }


    public void setCode(String code) {
        this.code = code;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}