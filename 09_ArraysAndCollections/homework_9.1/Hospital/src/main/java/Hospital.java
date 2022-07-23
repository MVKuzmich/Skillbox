import java.util.Arrays;

public class Hospital {

    public static final float MAX_TEMP_IN_HOSPITAL = 40f;
    public static final float MIN_TEMP_IN_HOSPITAL = 32f;

    public static final float MIN_TEMP_FOR_HEALTH = 36.2f;
    public static final float MAX_TEMP_FOR_HEALTH = 36.9f;

    public static float[] generatePatientsTemperatures(int patientsCount) {
        //TODO: напишите метод генерации массива температур пациентов
        float[] patientTemperatures = new float[patientsCount];
        for (int i = 0; i < patientsCount; i++) {
            float temperatures = (float) Math.floor((Math.random() * ((MAX_TEMP_IN_HOSPITAL - MIN_TEMP_IN_HOSPITAL))
                    + MIN_TEMP_IN_HOSPITAL)*10)/10;
            patientTemperatures[i] = temperatures;
        }
        return patientTemperatures;
    }

    public static String getReport(float[] temperatureData) {
        /*
        TODO: Напишите код, который выводит среднюю температуру по больнице,количество здоровых пациентов,
            а также температуры всех пациентов.
        */

        float averageTemp = 0;
        String regex = "[\\[\\],]";
        String tempForReport = Arrays.toString(temperatureData).replaceAll(regex, ""); // список температур

        // средняя температура
        float sumTemp = 0;

            for (int i = 0; i < temperatureData.length; i++) {
                sumTemp += temperatureData[i];
            }
            averageTemp = (float) (Math.round(sumTemp / temperatureData.length * 100)) / 100;

        // количество здоровых
        int countHealthyPerson = 0;
        for (int i = 0; i < temperatureData.length; i++) {
            if (temperatureData[i] <= MAX_TEMP_FOR_HEALTH && temperatureData[i] >= MIN_TEMP_FOR_HEALTH) {
                countHealthyPerson++;
            }
        }
        String report =
                "Температуры пациентов: " + tempForReport +
                        "\nСредняя температура: " + averageTemp +
                        "\nКоличество здоровых: " + countHealthyPerson;

        return report;
    }
}
