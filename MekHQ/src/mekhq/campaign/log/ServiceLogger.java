/*
 * Copyright (C) 2018 MegaMek team
 *
 * This file is part of MekHQ.
 *
 * MekHQ is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * MekHQ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MekHQ.  If not, see <http://www.gnu.org/licenses/>.
 */

package mekhq.campaign.log;

import megamek.common.util.EncodeControl;
import mekhq.campaign.personnel.Person;

import java.text.MessageFormat;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * This class is responsible to control the logging of Service Log Entries.
 * @author Miguel Azevedo
 */
public class ServiceLogger {

    private ResourceBundle logEntriesResourceMap;
    private static ServiceLogger serviceLogger = null;

    public ServiceLogger() {
        this.logEntriesResourceMap = ResourceBundle.getBundle("mekhq.resources.LogEntries", new EncodeControl());
    }

    public static ServiceLogger getInstance(){
        if(null == serviceLogger){
            serviceLogger = new ServiceLogger();
        }
        return serviceLogger;
    }

    public void retireDueToWounds(Person person, Date date){
        String message = logEntriesResourceMap.getString("retiredDueToWounds.text");
        person.addLogEntry(new ServiceLogEntry(date, MessageFormat.format(message, person.getGenderPronoun(person.PRONOUN_HISHER))));
    }

    public void madeBondsman(Person person, Date date, String name, String rankEntry){
        String message = logEntriesResourceMap.getString("madeBondsmanBy.text");
        person.addLogEntry(new ServiceLogEntry(date,
                MessageFormat.format(message, name) + rankEntry));
    }

    public void madeBondsman(Person person, Date date){
        person.addLogEntry(new ServiceLogEntry(date, logEntriesResourceMap.getString("madeBondsman.text")));
    }

    public void madePrisoner(Person person, Date date, String name, String rankEntry){
        String message = logEntriesResourceMap.getString("madePrisonerBy.text");
        person.addLogEntry(new ServiceLogEntry(date,
                MessageFormat.format(message, name) + rankEntry));
    }

    public void madePrisoner(Person person, Date date){
        person.addLogEntry(new ServiceLogEntry(date, logEntriesResourceMap.getString("madePrisoner.text")));
    }

    public void joined(Person person, Date date, String name, String rankEntry){
        String message = logEntriesResourceMap.getString("joined.text");
        person.addLogEntry(new ServiceLogEntry(date,
                MessageFormat.format(message, name) + rankEntry));
    }

    public void freed(Person person, Date date){
        person.addLogEntry(new ServiceLogEntry(date, logEntriesResourceMap.getString("freed.text")));
    }

    public void kia(Person person, Date date){
        person.addLogEntry(new ServiceLogEntry(date, logEntriesResourceMap.getString("kia.text")));
    }

    public void mia(Person person, Date date){
        person.addLogEntry(new ServiceLogEntry(date, logEntriesResourceMap.getString("mia.text")));
    }

    public void retired(Person person, Date date){
        person.addLogEntry(new ServiceLogEntry(date, logEntriesResourceMap.getString("retired.text")));
    }

    public void recoveredMia(Person person, Date date){
        person.addLogEntry(new ServiceLogEntry(date, logEntriesResourceMap.getString("recoveredMia.text")));
    }

    public void promotedTo(Person person, Date date){
        String message = logEntriesResourceMap.getString("promotedTo.text");
        person.addLogEntry(new ServiceLogEntry(date, MessageFormat.format(message, person.getRankName())));
    }

    public void demotedTo(Person person, Date date){
        String message = logEntriesResourceMap.getString("demotedTo.text");
        person.addLogEntry(new ServiceLogEntry(date, MessageFormat.format(message, person.getRankName())));
    }

    public void participatedInMission(Person person, Date date, String scenarioName, String missionName){
        String message = logEntriesResourceMap.getString("participatedInMission.text");
        person.addLogEntry(new ServiceLogEntry(date,
                MessageFormat.format(message, scenarioName, missionName)));
    }

    public void gainedXpFromMedWork(Person doctor, Date date, int taskXP){
        String message = logEntriesResourceMap.getString("gainedXpFromMedWork.text");
        doctor.addLogEntry(new ServiceLogEntry(date, MessageFormat.format(message, taskXP)));
    }

    public void successfullyTreatedWithXp(Person doctor, Person patient, Date date, int injuries, int xp){
        String message = logEntriesResourceMap.getString("successfullyTreatedWithXp.text");
        doctor.addLogEntry(new ServiceLogEntry(date, MessageFormat.format(message, patient, injuries, xp)));
    }

    public void successfullyTreated(Person doctor, Person patient, Date date, int injuries){
        String message = logEntriesResourceMap.getString("successfullyTreatedForXInjuries.text");
        doctor.addLogEntry(new ServiceLogEntry(date, MessageFormat.format(message, patient.getName(), injuries)));
    }

    public void assignedTo(Person person, Date date, String unitName){
        String message = logEntriesResourceMap.getString("assignedTo.text");
        person.addLogEntry(new ServiceLogEntry(date, MessageFormat.format(message, unitName)));
    }

    public void reassignedTo(Person person, Date date, String unitName){
        String message = logEntriesResourceMap.getString("reassignedTo.text");
        person.addLogEntry(new ServiceLogEntry(date, MessageFormat.format(message, unitName)));
    }

    public void removedFrom(Person person, Date date, String unitName){
        String message = logEntriesResourceMap.getString("removedFrom.text");
        person.addLogEntry(new ServiceLogEntry(date, MessageFormat.format(message, unitName)));
    }
}