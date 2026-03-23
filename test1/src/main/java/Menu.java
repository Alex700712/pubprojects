import Database.Entities.Key;
import Database.Entities.Organization;
import Database.Service.KeyService;
import Database.Service.OrganizationService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Menu {
    public static Scanner scanner = new Scanner(System.in);

    public  void StartMenu()
    {
        int choose_value = 0;
        while(true) {
            System.out.println("1) Organizations\n2) Keys\n0) Exit");
            choose_value = scanner.nextInt();
            if(choose_value == 1)
                OrgMenu();
            else if (choose_value == 2)
                KeyMenu();
            else if (choose_value == 0)
                break;
            else System.out.println("Wrong number\n");
        }
    }

    public void OrgMenu() {
        boolean back = false;

        while (!back) {
            OrganizationService service = new OrganizationService();
            System.out.println("1) Show List\n2) Add\n3) Update\n4) Delete\nAny Other - Back");
            int choose_value = scanner.nextInt();
            switch (choose_value) {
                case 1: {
                    List<Organization> orgList = service.findAllEntities();
                    for (Organization org : orgList)
                        System.out.println(org.getId() + " " +
                                org.getName());
                    System.out.println("----------------");
                    break;
                }
                case 2: {
                    System.out.println("Write Organization name\n");
                    Organization org = new Organization();
                    org.setName(scanner.next());
                    service.addEntity(org);
                    break;
                }
                case 3: {
                    System.out.println("Name of organization is only updateable field\n" +
                            "Write organization id\n");
                    Organization org = service.findEntityById(scanner.nextInt());

                    if (org != null) {
                        System.out.println("Write Organization name\n");
                        org.setName(scanner.next());
                        service.updateEntity(org);
                    } else System.out.println("Such entity doesn't exist\n");
                    break;
                }
                case 4: {
                    System.out.println("Write organization id\n");
                    Organization org = service.findEntityById(scanner.nextInt());

                    if (org != null) {
                        System.out.println("Confirm delete operation? (y/n)\n");
                        String confirm = scanner.next();
                        if (confirm.equals("y"))
                            service.deleteEntity(org);
                        else System.out.println("Deletion isn't completed");
                    } else System.out.println("Such entity doesn't exist\n");
                    break;
                }
                default:
                    back = true;
                    break;
            }
        }
    }

    public void KeyMenu()
    {
        boolean back = false;

        while (!back) {
            KeyService service = new KeyService();
            System.out.println("1) Show List\n2) Add\n3) Update\n4) Delete\n"
                    + "5) Find Key Info By Key\n" +
                    "6) Show Sorted Key List by Organizations\n" +
                    "Any Other - Back");
            int choose_value = scanner.nextInt();
            switch (choose_value) {
                case 1: {
                    List<Key> keyList = service.findAllEntities();
                    for (Key keyEntity : keyList)
                        System.out.println(keyEntity.getId() + " " +
                                keyEntity.getKey() + " " +
                                keyEntity.getStart_date().toString() +
                                " " + keyEntity.getEnd_date().toString() + " " +
                                keyEntity.getOrganization().getId() + " " +
                                keyEntity.GetBlockState());
                    System.out.println("----------------");
                    break;
                }
                case 2: {
                    Key key = new Key();
                    key.setKey(UUID.randomUUID());
                    key.setStart_date(Date.valueOf(LocalDate.now()));

                    LocalDate localdate = LocalDate.now();
                    System.out.println("Write days addition count (since now)\n");
                    localdate = localdate.plusDays(scanner.nextInt());
                    System.out.println("Write month addition count (since now)\n");
                    localdate = localdate.plusMonths(scanner.nextInt());
                    System.out.println("Write years addition count (since now)\n");
                    localdate = localdate.plusYears(scanner.nextInt());
                    key.setEnd_date(Date.valueOf(localdate));

                    System.out.println("Write organization id for connection\n");
                    OrganizationService orgServise = new OrganizationService();
                    Organization org = orgServise.findEntityById(scanner.nextInt());
                    if(org == null)
                    {
                        System.out.println("Such organization doesn't exist\n" +
                                "Entity isn't created");
                        break;
                    }
                    key.setOrganization(org);

                    key.setBlockState(false);

                    service.addEntity(key);
                    break;
                }
                case 3: {
                    System.out.println("Write key id");
                    Key key = service.findEntityById(scanner.nextInt());

                    if (key != null) {
                        System.out.println("Field to change:\n1) key\n 2)end date\n" +
                                "3)organization\n4)block state");
                        choose_value = scanner.nextInt();
                        if(choose_value == 1)
                        {
                            key.setKey(UUID.randomUUID());
                            service.updateEntity(key);
                        }
                        else if(choose_value == 2)
                        {
                            System.out.println("Start date is " + key.getStart_date().toString());

                            LocalDate localdate = LocalDate.now();
                            System.out.println("Write days addition count (since start date)\n");
                            localdate = localdate.plusDays(scanner.nextInt());
                            System.out.println("Write month addition count (since start date)\n");
                            localdate = localdate.plusMonths(scanner.nextInt());
                            System.out.println("Write years addition count (since start date)\n");
                            localdate = localdate.plusYears(scanner.nextInt());
                            key.setEnd_date(Date.valueOf(localdate));
                            service.updateEntity(key);
                        }
                        else if (choose_value == 3)
                        {
                            System.out.println("Write new organization id for connection\n");
                            OrganizationService orgServise = new OrganizationService();
                            Organization org = orgServise.findEntityById(scanner.nextInt());
                            if(org == null)
                            {
                                System.out.println("Such organization doesn't exist\n" +
                                        "Entity isn't updated");
                                break;
                            }
                            key.setOrganization(org);
                            service.keyDao.update(key);
                        }
                        else if (choose_value == 4)
                        {
                            if(!key.GetBlockState())
                                System.out.println("Confirm blocking of key "
                                        + key.getKey() + " (y/n)" );
                            else System.out.println("Confirm unblocking of key "
                                    + key.getKey() + " (y/n)" );


                            if(scanner.next().equals("y"))
                                key.setBlockState(!key.GetBlockState());
                            else System.out.println("Blocking state isn't changed");


                            service.keyDao.update(key);
                        }
                        else System.out.println("Wrong menu line number\n");

                    } else System.out.println("Such entity doesn't exist\n");
                    break;
                }
                case 4: {
                    System.out.println("Write organization id\n");
                    Key key = service.findEntityById(scanner.nextInt());

                    if (key != null) {
                        System.out.println("Confirm delete operation? (y/n)\n");
                        String confirm = scanner.next();
                        if (confirm.equals("y"))
                            service.deleteEntity(key);
                        else System.out.println("Deletion isn't completed");
                    } else System.out.println("Such entity doesn't exist\n");
                    break;
                }
                case 5:
                {
                    System.out.print("Write Unique Key: ");
                    Key keyEntity = service.findEntity(UUID.fromString(scanner.next()));
                    if(keyEntity != null)
                    {
                        System.out.println(keyEntity.getId() + " " +
                                keyEntity.getKey() + " " +
                                keyEntity.getStart_date().toString() +
                                " " + keyEntity.getEnd_date().toString() + " " +
                                keyEntity.getOrganization().getId() + " " +
                                keyEntity.GetBlockState());
                        System.out.println("----------------");
                        break;
                    }
                    else System.out.println("Such entity doesn't exist\n");
                }
                case 6:
                {
                    List<Key> keyList = service.keyDao.getSortedByOrg();
                    for (Key keyEntity : keyList)
                        System.out.println(keyEntity.getId() + " " +
                                keyEntity.getKey() + " " +
                                keyEntity.getStart_date().toString() +
                                " " + keyEntity.getEnd_date().toString() + " " +
                                keyEntity.getOrganization().getId() + " " +
                                keyEntity.GetBlockState());
                    System.out.println("----------------");
                    break;
                }
                default:
                    back = true;
                    break;
            }
        }
    }
}
