public class Name {
    private String name;
    private String gender;
    private int[] count;
    private int[] rank;

    
    public Name(String name) {
        this.name = name;
        this.gender = "";
        this.count = new int[2026];
        this.rank = new int[2026];
    }

    public int getRank(int year) {
        return rank[year];
    }
    public String getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }
    

    public int getCount(int year) {
        return count[year];
    }

    public int getYearCount() {
        return count.length;
    }

    public void setRank(int year, int rank) {
        this.rank[year] = rank;
    }

    public void setCount(int year, int count) {
        this.count[year] = count;
    }

}