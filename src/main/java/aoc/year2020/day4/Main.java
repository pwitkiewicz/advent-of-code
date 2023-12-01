package aoc.year2020.day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        BufferedReader reader;

        int validPassports = 0;

        try {
            reader = new BufferedReader(new FileReader("input-day4.txt"));
            String line = reader.readLine();

            while (line != null) {
                HashMap<String, String> passport = new HashMap<>();

                String nextline = reader.readLine();
                while(nextline != null && !nextline.equals("")) {
                    line += " " + nextline;
                    nextline = reader.readLine();
                }

                String[] fields = line.split("\\s+");

                for(String field : fields) {
                    passport.put(field.split(":")[0], field.split(":")[1]);
                }


                if(passport.size() == 8 || (passport.size() == 7 && !passport.containsKey("cid"))) {
                    boolean validity = true;

                    for(String key : passport.keySet()) {
                        if(key.equals("byr")) {
                            int year = Integer.parseInt(passport.get("byr"));

                            if(year < 1920 || year > 2002) {
                                validity = false;
                                break;
                            }
                        }

                        if(key.equals("iyr")) {
                            int year = Integer.parseInt(passport.get("iyr"));

                            if(year < 2010 || year > 2020) {
                                validity = false;
                                break;
                            }
                        }

                        if(key.equals("eyr")) {
                            int year = Integer.parseInt(passport.get("eyr"));

                            if(year < 2020 || year > 2030) {
                                validity = false;
                                break;
                            }
                        }

                        if(key.equals("hgt")) {
                            Pattern pat = Pattern.compile("^(\\d+)$");
                            Matcher m = pat.matcher(passport.get("hgt"));

                            if(m.find()) {
                                validity = false;
                                break;
                            }

                            pat = Pattern.compile("^(\\d+)");
                            m = pat.matcher(passport.get("hgt"));

                            int num = 0;

                            if(m.find()) {
                                num = Integer.parseInt(m.group());
                            }

                            String unit = passport.get("hgt").split("^(\\d+)")[1];

                            if(unit.equals("cm")) {
                                if(num < 150 || num > 193) {
                                    validity = false;
                                    break;
                                }
                            }

                            if(unit.equals("in")) {
                                if(num < 59 || num > 76) {
                                    validity = false;
                                    break;
                                }
                            }
                        }

                        if(key.equals("hcl")) {
                            String value = passport.get("hcl");
                            Pattern pat = Pattern.compile("^(#)([0-9a-f]{6})");
                            Matcher m = pat.matcher(value);

                            if(!m.find()) {
                                validity = false;
                                break;
                            }
                        }

                        if(key.equals("ecl")) {
                            String value = passport.get("ecl");
                            if(!value.equals("amb") && !value.equals("blu")
                                    && !value.equals("brn")
                                    && !value.equals("gry")
                                    && !value.equals("grn")
                                    && !value.equals("hzl")
                                    && !value.equals("oth")) {
                                validity = false;
                                break;
                            }
                        }

                        if(key.equals("pid")) {
                            String value = passport.get("pid");
                            Pattern pat = Pattern.compile("^([0-9]{9})$");
                            Matcher m = pat.matcher(value);

                            if(!m.find()) {
                                validity = false;
                                break;
                            }
                        }
                    }

                    if(validity) {
                        validPassports++;
                    }
                }

                line = reader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(validPassports);
    }
}
