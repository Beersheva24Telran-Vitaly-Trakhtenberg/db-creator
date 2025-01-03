package telran.databases;

import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

public class GenerateCSV
{
    public enum Department {
        QA, Development, DevOps, Sales
    }

    public enum Type {
        Manager, Employee, WageEmployee, SalesPerson
    }

    private static int last_id = 1000;

    public static void main(String[] args) throws IOException
    {
        String file_path = "employees_sql.csv";

        try (CSVWriter writer = new CSVWriter(new FileWriter(file_path))) {
            writer.writeNext(new String[]{
                    "table_id",
                    "name",
                    "family_name",
                    "birthday",
                    "phone",
                    "department",
                    "type",
                    "basic_salary",
                    "factor",
                    "wage",
                    "hours",
                    "percent",
                    "sales"
            });

            createEmployees(writer, Department.QA, Type.Manager,  1);
            createEmployees(writer, Department.QA, Type.Employee,  2);
            createEmployees(writer, Department.QA, Type.WageEmployee,  10);

            createEmployees(writer, Department.Development, Type.Manager,  1);
            createEmployees(writer, Department.Development, Type.WageEmployee,  30);

            createEmployees(writer, Department.DevOps, Type.Manager,  1);
            createEmployees(writer, Department.DevOps, Type.Employee,  5);

            createEmployees(writer, Department.Sales, Type.Manager,  1);
            createEmployees(writer, Department.Sales, Type.Employee,  3);
        }
    }

    private static void createEmployees(CSVWriter writer, Department department, Type type, int number_employee)
    {
        Random random = new Random();
        for (int i = 0; i < number_employee; i++) {
            String[] row = new String[13];
            last_id++;
            GregorianCalendar calendar = RandomGenerator(1960, 2005);

            row[0] = Integer.toString(last_id);
            row[1] = "Name " + last_id;
            row[2] = "Family_Name " + last_id;
            row[3] = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
            row[4] = "+" + random.nextInt(1000000000);
            row[5] = String.valueOf(department);
            row[6] = String.valueOf(type);
            row[7] = Integer.toString(4999 + random.nextInt(50001));

            switch (type) {
                case Manager:
                    row[8] = Float.toString(1.5F + random.nextFloat(4.01F));
                    break;
                case Employee:
                    break;
                case WageEmployee:
                    row[9] = Integer.toString(29 + random.nextInt(301));
                    row[10] = Integer.toString(random.nextInt(201));
                    break;
                case SalesPerson:
                    row[9] = Integer.toString(29 + random.nextInt(301));
                    row[10] = Integer.toString(random.nextInt(201));
                    row[11] = Float.toString(0.1F + random.nextFloat(1.51F));
                    row[12] = Long.toString(random.nextLong(Long.MAX_VALUE));
                    break;
            }

            writer.writeNext(row);
        }
    }

    private static GregorianCalendar RandomGenerator(int startYear, int endYear)
    {
        Random rnd = new Random();
        int year = startYear + rnd.nextInt(endYear - startYear);
        int month = rnd.nextInt(12) + 1;
        int day = rnd.nextInt(28) + 1;

        return new GregorianCalendar(year, month, day);
    }
}
