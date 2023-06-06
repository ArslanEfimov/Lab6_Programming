package main.java.shared.managers;

import main.java.shared.core.Exceptions.IncorrectValueException;
import main.java.shared.core.models.*;
import main.java.shared.io.UserIO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Vector;
import java.util.stream.Collectors;

@XmlRootElement(name = "organizations")
public class CollectionManager {
    @XmlElement(name = "organization")
    private Vector<Organization> organizationVector = new Vector<>();
    private FileManagerReader fileManagerReader;
    private LinkedList<Long> listForGenerateId = new LinkedList<>();

    public CollectionManager(FileManagerReader fileManagerReader){
        this.fileManagerReader = fileManagerReader;
        organizationVector = fileManagerReader.readCollection();
        Collections.sort(organizationVector);
    }
    public CollectionManager(){}


    public Vector<Organization> getCollectionVector(){
        return this.organizationVector;

    }

    @Override
    public String toString(){
        return "Тип коллекции: Vector"
                + "\n" + "Количество элементов в коллекции: " + organizationVector.size() +"\n"
                + "Дата создания коллекции: " + getCollectionVector().firstElement().getCreationDate().toString();

    }
    public String clearCollection(){
        this.organizationVector = new Vector<>();
        return "Коллекция успешно очищена!";
    }

    public String removeFirst(){
        this.organizationVector = organizationVector.stream().skip(1).collect(Collectors.toCollection(Vector::new));
        return "Первый элемент успешно удален!";
    }
    public void addNewElement(Organization organization){
        organizationVector.add(organization);
    }
    public boolean greaterThanMax(Organization organization){
        if(organization.getAnnualTurnover()>organizationVector.stream().filter(org->org.getAnnualTurnover()!=0).
        map(Organization::getAnnualTurnover).max(Comparator.naturalOrder()).orElse(null)){
            return true;
        }
        else{
            return false;
        }
    }
    public void fillListId(){
        for(Organization organization : organizationVector){
            listForGenerateId.add(organization.getId());
        }
    }

    public Long generateId(){
        Long id;
        fillListId();
        boolean flag = true;
        while (flag) {
            id = (long) (1L + (Math.random() * (listForGenerateId.size()+1)));
            if (!listForGenerateId.contains(id)) {
                listForGenerateId.add(id);
                return id;
            }
        }
        return null;
    }
    public boolean checkId(Long id){
        if (listForGenerateId.contains(id)) {
            return true;
        }
        else{
            return false;
        }

    }

    public int countGreaterThanOfficicalAddress(Address officialAddress){
        int count = 0;
        for (Organization organization : getCollectionVector()) {
            int result = organization.getOfficialAddress().getStreet().compareTo(String.valueOf(officialAddress));
            if (result > 0) {
                count += 1;
            }
        }
        return count;
    }
    public boolean removeById(Long id){
        return (getCollectionVector().removeIf(elem -> elem.getId() == id));
    }
    public boolean removeGreater(Float annualTurnover){
        return (getCollectionVector().removeIf(elem -> elem.getAnnualTurnover().equals(annualTurnover)));
    }
    public void checkCollection(Vector<Organization> vectorOrganization){
        UserIO userIO = new UserIO();
        try{
            for(Organization organization : vectorOrganization){
                organization.setId(organization.getId());
                if(checkId(organization.getId())) throw new IncorrectValueException("идентификатор должен быть уникальным");
                listForGenerateId.add(organization.getId());
                organization.setName(organization.getName());
                organization.getCoordinates().setX(organization.getCoordinates().getX());
                organization.getCoordinates().setY(organization.getCoordinates().getY());
                organization.setCreationDate(organization.getCreationDate());
                organization.setType(organization.getType());
                organization.setFullName(organization.getFullName());
                organization.getOfficialAddress().setStreet(organization.getOfficialAddress().getStreet());
                organization.setAnnualTurnover(organization.getAnnualTurnover());
            }
            this.organizationVector = vectorOrganization;
        }catch (IllegalArgumentException | IncorrectValueException ex){
            userIO.println(ex.getMessage());
            userIO.println("Данные в файле неверны, проверьте, возможно у вас нет нужных тегов");
            this.organizationVector = new Vector<>();

        }
    }


}
