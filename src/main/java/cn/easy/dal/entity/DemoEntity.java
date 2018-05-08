package cn.easy.dal.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by superlee on 2017/11/23.
 */
@Entity
@Table(name = "t_demo")
public class DemoEntity {
    private String id;
    private String demoField;

    @Id
    @GenericGenerator(name = "id_uuid", strategy = "uuid")
    @GeneratedValue(generator = "id_uuid")
    @Column(name = "id", nullable = false, unique = true, length = 40)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "demo_field", nullable = false)
    public String getDemoField() {
        return demoField;
    }

    public void setDemoField(String demoField) {
        this.demoField = demoField;
    }

    @Override
    public String toString() {
        return "DemoEntity{" +
                "id='" + id + '\'' +
                ", demoField='" + demoField + '\'' +
                '}';
    }
}
