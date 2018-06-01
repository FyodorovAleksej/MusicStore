package by.fyodorov.musicstore.model;

/**
 * class entity of performer
 */
public class PerformerEntity extends EntityBase {
    private int id;
    private String name;

    public PerformerEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "id = " + this.id + "\n" +
                "name = \"" + this.name + "\"";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        PerformerEntity performer = (PerformerEntity) obj;
        return performer.id == this.id && performer.name.equals(this.name);
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id) +
                name.hashCode();
    }
}
