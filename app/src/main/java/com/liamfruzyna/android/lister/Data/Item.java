package com.liamfruzyna.android.lister.Data;

import android.provider.ContactsContract;

import com.liamfruzyna.android.lister.Activities.WLActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/*this is the object for items that are added into lists
    Boolean done - whether the item is checked off or not
    Boolean archived - whether the item has been hidden
            this will be ignored if the setting is checked
            but set grey
    String item - the text in the item
*/
public class Item
{
    public Boolean done;
    public String item;
    public Boolean priority;

    public String color = "#000000";
    public List<String> people = new ArrayList<>();
    public List<String> tags = new ArrayList<>();
    public Date date = new Date(1097, 3, 24);

    //the constructor if you don't know what this is don't ask me
    public Item(String item, Boolean done)
    {
        this.item = item;
        parseItem();
        this.done = done;
    }

    //looks for tags within a list item
    public void parseItem()
    {
        if(item.contains("@"))
        {
            //person tag
            String[] people = item.split("@");
            findPeople(people);
        }
        if(item.contains("*"))
        {
            priority = true;
        }
        else
        {
            priority = false;
        }
        if(item.contains("#"))
        {
            //tag
            String[] tags = item.split("\\#");
            findTags(tags);
        }
        if(item.contains("/"))
        {
            //date
            String[] date = item.split("/");
            if(date.length == 3)
            {
                //if the date has month day and year
                int day = Integer.parseInt(date[1]);
                int month;
                int year;
                if (date[0].contains(" ")) {
                    String[] start = date[0].split(" ");
                    month = Integer.parseInt(start[start.length - 1]);
                } else {
                    month = Integer.parseInt(date[0]);
                }
                month -= 1;
                if (date[2].contains(" ")) {
                    String[] end = date[2].split(" ");
                    year = Integer.parseInt(end[0]);
                } else {
                    year = Integer.parseInt(date[2]);
                }
                if(year < 2000)
                {
                    year += 2000;
                }
                System.out.println("Found date: " + month + "/" + day + "/" + year);
                this.date = new Date(year, month, day);
            }
            else if(date.length == 2)
            {
                //if the date just has month and day
                int day;
                int month;
                int year;
                if (date[0].contains(" ")) {
                    String[] start = date[0].split(" ");
                    month = Integer.parseInt(start[start.length - 1]);
                } else {
                    month = Integer.parseInt(date[0]);
                }
                month -= 1;
                if (date[1].contains(" ")) {
                    String[] end = date[1].split(" ");
                    day = Integer.parseInt(end[0]);
                } else {
                    day = Integer.parseInt(date[1]);
                }
                year = Calendar.getInstance().getTime().getYear();
                System.out.println("Found date: " + month + "/" + day + "/" + year);
                this.date = new Date(year, month, day);
            }
        }
    }

    //finds all the people in all the list's items
    public void findPeople(String[] strings)
    {
        for(int i = 1; i < strings.length; i++)
        {
            String person = strings[i];
            if (person.contains(" "))
            {
                person = person.split(" ")[0];
            }
            System.out.println("Found Person: " + person);
            people.add(person);
        }
    }

    //finds all the tags in all the list's items    
    public void findTags(String[] strings)
    {
        for(int i = 1; i < strings.length; i++)
        {
            String tag = strings[i];
            if (tag.contains(" "))
            {
                tag = tag.split(" ")[0];
            }
            System.out.println("Found Tag: " + tag);
            tags.add(tag);

            if(isColor(tag))
            {
                //color
                String color = tag;
                if(color.contains(" "))
                {
                    color = color.split(" ")[0];
                }
                this.color = "#" + color;
                System.out.println("Found Color: " + this.color);
            }
        }
    }

    public boolean isColor(String color)
    {
        color = color.toLowerCase();
        if(color.length() == 6)
        {
            for(int i = 0; i < color.length(); i++)
            {
                char c = color.charAt(i);
                if(c != 'a' && c != 'b' && c != 'c' && c != 'c' && c != 'e' && c != 'f' &&
                        c != '0' && c != '1' && c != '2' && c != '3' && c != '4' && c != '5' && c != '6' && c != '7' && c != '8' && c != '9')
                {
                    return false;
                }
            }
            return true;
        }
        else
        {
            return false;
        }
    }
}