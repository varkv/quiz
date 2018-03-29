package com.epam.kvk.quiz.migration;

import com.epam.kvk.quiz.entity.Question;
import com.epam.kvk.quiz.entity.enums.QuestionTypeEnum;
import com.epam.kvk.quiz.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
@Profile("DB-quiz-1")
public class QuestionMigration {

    @Autowired
    QuestionRepository questionRepository;

    private List<Question> getQuestions(){
        return new ArrayList<Question>(){{
            add(new Question(
                    "Какие сравнения напечатают true?",
                    "<pre>Long a = 111l;\n" +
                            "Long b = 111l;\n" +
                            "Long c = 222l;\n" +
                            "Long d = 222l;\n" +
                            "\n" +
                            "System.out.println(a == b); //A.\n" +
                            "System.out.println(c == d); //B.</pre>",
                    QuestionTypeEnum.RADIO,
                    new ArrayList<String>(){{
                        add("Только A");
                        add("Только B");
                        add("Все");
                        add("Никакие");
                    }},
                    new ArrayList<String>(){{
                        add("Только A");
                    }},
                    "https://youtu.be/AR9dtVaEUSM?t=17m52s"
            ));

            add(new Question(
                    "Какое значение получит переменная <b>j</b> после прохода цикла?",
                    "<pre>int j = 0;\n" +
                            "for (int i = 0; i < 100; i++) \n" +
                            "    j = j++;\n" +
                            "\n" +
                            "System.out.println(j);</pre>",
                    QuestionTypeEnum.RADIO,
                    new ArrayList<String>(){{
                        add("100");
                        add("101");
                        add("0");
                        add("1");
                    }},
                    new ArrayList<String>(){{
                        add("0");
                    }},
                    "https://youtu.be/yN8jpsQuYxk?t=5m6s"
            ));

            add(new Question(
                    "Результат вызова метода work()?",
                    "<pre>private void work() {\n" +
                            "    try {\n" +
                            "        work();\n" +
                            "    } finally {\n" +
                            "        work();\n" +
                            "    }\n" +
                            "}</pre>",
                    QuestionTypeEnum.RADIO,
                    new ArrayList<String>(){{
                        add("StackOverflowException");
                        add("NullPointerException");
                        add("Зависнет");
                        add("Успешно завершит метод");
                    }},
                    new ArrayList<String>(){{
                        add("Зависнет");
                    }},
                    "https://youtu.be/AR9dtVaEUSM?t=21m2s"
            ));

            add(new Question(
                    "Что работает быстрее?",
                    "<p> Возьмем Integer <b>NUM</b> и преобразуем его в строку такими способами: </p>" +
                            "\n<pre>//A.\n" +
                            "Integer.toString(NUM);\n" +
                            "//B.\n" +
                            "\"\" + NUM;\n" +
                            "//C.\n" +
                            "StringBuilder builder = new StringBuilder();\n" +
                            "builder.append(NUM);\n" +
                            "builder.toString()</pre>",
                    QuestionTypeEnum.RADIO,
                    new ArrayList<String>(){{
                        add("A");
                        add("B");
                        add("C");
                    }},
                    new ArrayList<String>(){{
                        add("B");
                    }},
                    "https://youtu.be/wgQBz2Ldhvk?t=26m55s"
            ));
            add(new Question(
                    "Что работает быстрее?",
                    "<p> Дадим StringBuilder еще один шанс и попробуем такой вариант: </p>" +
                            "\n<pre>//A.\n" +
                            "String str = \"\";\n" +
                            "for (int i = 0; i < 100; ++i) {\n" +
                            "    str += i;\n" +
                            "}\n" +
                            "\n" +
                            "//B.\n" +
                            "StringBuilder builder = new StringBuilder();\n" +
                            "for (int i = 0; i < 100; ++i) {\n" +
                            "    builder.append(i);\n" +
                            "}</pre>",
                    QuestionTypeEnum.RADIO,
                    new ArrayList<String>(){{
                        add("A");
                        add("B");
                    }},
                    new ArrayList<String>(){{
                        add("B");
                    }},
                    "https://youtu.be/wgQBz2Ldhvk?t=33m18s"
            ));

            add(new Question(
                    "Какой порядок вывода (Java 8)?",
                    "<pre>Set<String> accounts = Set.of(\"Gates\", \"Buffett\", \"Bezos\", \"Zucherberg\");\n" +
                            "System.out.println(\"accounts= \" + accounts);</pre>",
                    QuestionTypeEnum.RADIO,
                    new ArrayList<String>(){{
                        add("Порядок сохраняется");
                        add("Порядок неизвестен, но сохраняется между запусками");
                        add("Порядок неизвестен и меняется при каждом перезапуске JVM");
                        add("Порядок неизвестен и меняется при каждой распечатке");
                    }},
                    new ArrayList<String>(){{
                        add("Порядок неизвестен и меняется при каждом перезапуске JVM");
                    }},
                    "https://youtu.be/hCsh60x8ZoU?t=25m"
            ));

            add(new Question(
                    "Результат двух сравнений?",
                    "<pre>Spliterators.emptySpliterator() == Spliterators.emptySpliterator(); \n" +
                            "Stream.empty() == Stream.empty();</pre>",
                    QuestionTypeEnum.RADIO,
                    new ArrayList<String>(){{
                        add("true/true");
                        add("true/false");
                        add("false/true");
                        add("false/false");
                    }},
                    new ArrayList<String>(){{
                        add("true/false");
                    }},
                    "https://youtu.be/hCsh60x8ZoU?t=9m29s"
            ));

            add(new Question(
                    "Что выведет на экран?",
                    "<p>Раз так то что будет в таком случае?</p>\n" +
                            "<pre>List<String> list = new ArrayList<>();\n" +
                            "list.add(\"молоко\");\n" +
                            "list.add(\"хлеб\");\n" +
                            "list.add(\"колбаса\");\n" +
                            "Stream<String> stream = list.stream();\n" +
                            "list.add(\"яйца, яйца ещё\");\n" +
                            "stream.forEach(System.out::println);</pre>",
                    QuestionTypeEnum.RADIO,
                    new ArrayList<String>(){{
                        add("молоко/хлеб/колбаса");
                        add("молоко/хлеб/колбаса/яйца, яйца ещё");
                        add("молоко/хлеб/колбаса/ConcurrentModificationException");
                        add("ConcurrentModificationException");
                    }},
                    new ArrayList<String>(){{
                        add("молоко/хлеб/колбаса/ConcurrentModificationException");
                    }},
                    "https://youtu.be/H_q14bzBOIk?t=5m36s"
            ));

            add(new Question(
                    "Результат работы блока кода?",
                    "<p>А если после всего в цикле добавим?</p>\n" +
                            "<pre>List<String> list = new ArrayList<>();\n" +
                            "list.add(\"One\");\n" +
                            "list.add(\"Two\");\n" +
                            "list.add(\"Three\");\n" +
                            "\n" +
                            "list.stream().forEach(s ->{\n" +
                            "    System.out.println(s);\n" +
                            "    list.add(s + \"New\");\n" +
                            "});</pre>",
                    QuestionTypeEnum.RADIO,
                    new ArrayList<String>(){{
                        add("Не скомпилируется");
                        add("One + исключение");
                        add("One Two Three + исключение");
                        add("Зациклится");
                    }},
                    new ArrayList<String>(){{
                        add("One Two Three + исключение");
                    }},
                    "https://youtu.be/lhXQZGuNyUk?t=5m27s"
            ));



            add(new Question(
                    "Результат компиляции?",
                    "<pre>public interface Сэм<T>{\n" +
                            "    default void расширятьНато(String новаяСтрана){\n" +
                            "        System.out.println(новаяСтрана);\n" +
                            "    }\n" +
                            "    void расширятьНато(T страна);\n" +
                            "    void захватыватьНефть(T страна);\n" +
                            "    void захватыватьНефть(String страна);\n" +
                            "}\n" +
                            "\n" +
                            "@FunctionalInterface\n" +
                            "public interface ДядяСэм extends Сэм<String>{\n" +
                            "    \n" +
                            "}</pre>",
                    QuestionTypeEnum.RADIO,
                    new ArrayList<String>(){{
                        add("Написан непонятный бред");
                        add("Проблема с методом расширятьНато(T), если его убрать, все ок.");
                        add("Проблема с методами захватыватьНефть, если один убрать, все будет ок.");
                        add("Все путем! Дубликаты схлопываются, и мы останемся с одним захватыватьНефть.");
                    }},
                    new ArrayList<String>(){{
                        add("Проблема с методом расширятьНато(T), если его убрать, все ок.");
                    }},
                    "https://youtu.be/hCsh60x8ZoU?t=18m30s"
            ));

            add(new Question(
                    "В чем разница между строчками 1 и 2?",
                    "<pre>public void killAll(){\n" +
                            "    ExecutorService ex = Exectors.newSingleThreadExecutor();\n" +
                            "    List<String> sentence = Arrays.asList(\"Казнить\");\n" +
                            "    ex.submit(() -> Files.write(Paths.get(\"Приговор.txt\"), sentence)); // 1\n" +
                            "    ex.submit(() -> { Files.write(Paths.get(\"Приговор.txt\"), sentence) }); // 2\n" +
                            "}</pre>",
                    QuestionTypeEnum.RADIO,
                    new ArrayList<String>(){{
                        add("1 компилируется, 2 нет");
                        add("2 компилируется, 1 нет");
                        add("С обоими все ок.");
                        add("Обе не компилируются.");
                    }},
                    new ArrayList<String>(){{
                        add("1 компилируется, 2 нет");

                    }},
                    "https://youtu.be/H_q14bzBOIk?t=11m10s"
            ));

            add(new Question(
                    "Аннотации Spring в наследовании?",
                    "<p>Есть класс Parent с инит методом и есть наследник с таким же методом. Мы создаем с помощью спринга объект из Сына. Вопрос что будет с методом инит у папы, сработает или нет? </p>\n" +
                            "<pre>\n" +
                            "    public class Parent {\n" +
                            "        \n" +
                            "        @PostConstruct\n" +
                            "        private void init(){\n" +
                            "            System.out.println(\"Папа\");\n" +
                            "        }\n" +
                            "    }\n" +
                            "    \n" +
                            "    @Component\n" +
                            "    public class Son extends Parent{\n" +
                            "\n" +
                            "        @PostConstruct\n" +
                            "        public void init(){\n" +
                            "            System.out.println(\"Сын\");\n" +
                            "        }\n" +
                            "        \n" +
                            "    }</pre>",
                    QuestionTypeEnum.RADIO,
                    new ArrayList<String>(){{
                        add("@PostConstruct не inherited поэтому Папа не придет");
                        add("init у Папы поэтому Папа не придет");
                        add("Не может быть больше, чем один init method поэтому Папа не придет");
                        add("init method переопределен у Сына поэтому Папа не придет");
                        add("Папа придет");
                    }},
                    new ArrayList<String>(){{
                        add("Папа придет");
                    }},
                    "https://youtu.be/U8MtGYa04v8?t=4m2s"
            ));

            add(new Question(
                    "Самовпрыскивание в spring 4. Как сделать?",
                    "<p>Можно ли в самом себе поставить так Autowired на тот же тип что и объект и получить его прокси: </p>\n" +
                            "<pre>\n" +
                            "@Service\n" +
                            "public class JokerConfService{\n" +
                            "    \n" +
                            "    @Autowired\n" +
                            "    JokerConfService proxy;\n" +
                            "    \n" +
                            "    public void pay(){\n" +
                            "        proxy.infomAboutPayment();\n" +
                            "    }\n" +
                            "    \n" +
                            "    private void infomAboutPayment(){\n" +
                            "        System.out.println(\"Payed!\");\n" +
                            "    }\n" +
                            "}</pre>",
                    QuestionTypeEnum.RADIO,
                    new ArrayList<String>(){{
                        add("@Autowired");
                        add("@Inject");
                        add("@Resource");
                        add("Так нельзя сделать");
                    }},
                    new ArrayList<String>(){{
                        add("@Resource");
                    }},
                    "https://youtu.be/U8MtGYa04v8?t=24m48s"
            ));

        }};
    }

    @PostConstruct
    private void init(){
        List<Question> questions = getQuestions();
//        questions = questions.subList(questions.size() - 1, questions.size());
//        Collections.shuffle(questions);
        questionRepository.save(questions);
    }

    void test(){

    }





}
