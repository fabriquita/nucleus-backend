package org.fabriquita.nucleus.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "entity0")
public class NEntity extends Mappable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    
    @Column(nullable = false)
    String name;
    
    @Lob
    @Column(nullable = false)
    String content;
    
    @Column(nullable = false)
    Boolean active;
    
    @ManyToOne
    @JoinColumn(name = "collection_id", nullable = false)
    private NCollection nCollection;
    
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    Group group;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    
    public NCollection getNCollection() {
        return nCollection;
    }
    
    public void setNCollection(NCollection nCollection) {
        this.nCollection = nCollection;
    }
    
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
    
    @Override
    public Object mappableId() {
        return this.id;
    }
}
