//TODO придумать другое имя
class Class {

    private String name;
    private long date;

    public Class(String name, long date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Class{" +
                "name='" + name + '\'' +
                ", date=" + date +
                '}';
    }
}
