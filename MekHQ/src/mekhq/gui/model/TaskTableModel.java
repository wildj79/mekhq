package mekhq.gui.model;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import megamek.common.TargetRoll;
import mekhq.IconPackage;
import mekhq.campaign.parts.MissingPart;
import mekhq.campaign.parts.Part;
import mekhq.campaign.personnel.Person;
import mekhq.gui.BasicInfo;
import mekhq.gui.CampaignGUI;

/**
 * A table model for displaying work items
 */
public class TaskTableModel extends DataTableModel {
    private static final long serialVersionUID = -6256038046416893994L;

    private CampaignGUI campaignGUI;
    
    private interface REPAIR_STATE {
    	public static final int AVAILABLE = 0;
    	public static final int NOT_AVAILABLE = 1;
    	public static final int IN_TRANSIT = 2;
    	public static final int BLOCKED = 3;
    	public static final int SCHEDULED = 4;
    }
    
    public TaskTableModel(CampaignGUI campaignGUI) {
        columnNames = new String[] { "Tasks" };
        data = new ArrayList<Part>();
        
        this.campaignGUI = campaignGUI;
    }

    public Object getValueAt(int row, int col) {
        return ((Part) data.get(row)).getDesc();
    }

    public Part getTaskAt(int row) {
        return (Part) data.get(row);
    }

    public Part[] getTasksAt(int[] rows) {
        Part[] tasks = new Part[rows.length];
        for (int i = 0; i < rows.length; i++) {
            int row = rows[i];
            tasks[i] = (Part) data.get(row);
        }
        return tasks;
    }

    public TaskTableModel.Renderer getRenderer(IconPackage icons) {
        return new TaskTableModel.Renderer(icons);
    }

    public class Renderer extends BasicInfo implements TableCellRenderer {

        public Renderer(IconPackage icons) {
            super(icons);
        }

        private static final long serialVersionUID = -3052618135259621130L;

        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            Component c = this;
            int actualCol = table.convertColumnIndexToModel(column);
            int actualRow = table.convertRowIndexToModel(row);
            
            setOpaque(true);
            setText("<html>" + getValueAt(actualRow, actualCol).toString() + "</html>", "black");
            if (isSelected) {
                highlightBorder();
            } else {
                unhighlightBorder();
            }
            
            Part part = getTaskAt(actualRow);
            
            int availableLevel = REPAIR_STATE.AVAILABLE;
            
            if (!part.isSalvaging()) {
	            if (null != part.getTeamId()) {
	            	availableLevel = REPAIR_STATE.SCHEDULED;
	            } else {
	            	if (part instanceof MissingPart) {
	            		/*
	            		String[] inventories = campaignGUI.getCampaign().getPartInventory(part);
	
	            		String inStockStr = inventories[0];
	            		String inTransitStr = inventories[1];
	            		String onOrderStr = inventories[2];
	            		*/
	            		
	            		if (!((MissingPart)part).isReplacementAvailable()) {
	            			availableLevel = REPAIR_STATE.NOT_AVAILABLE;	            			
	            		}
	            	}
	            	
	            	if (availableLevel == REPAIR_STATE.AVAILABLE) {
		                Person tech = campaignGUI.getSelectedTech();
		            	
		                if (null != tech) {
		                	TargetRoll roll = campaignGUI.getCampaign().getTargetFor(part, tech);
		                	
		                	if ((roll.getValue() == TargetRoll.IMPOSSIBLE) || (roll.getValue() == TargetRoll.AUTOMATIC_FAIL) || (roll.getValue() == TargetRoll.CHECK_FALSE)) {
		                		availableLevel = REPAIR_STATE.BLOCKED;
		                	}
		                }
	            	}
	            }
            }
            
            String imgMod = "";
            
            switch (availableLevel) {
	            case REPAIR_STATE.BLOCKED:
	            	imgMod = "_impossible";	            	
	            	break;
	            	
	            case REPAIR_STATE.IN_TRANSIT:
	            	imgMod = "_transit";	            	
	            	break;
	            	
	            case REPAIR_STATE.NOT_AVAILABLE:
	            	imgMod = "_na";	            	
	            	break;
	            	
	            case REPAIR_STATE.SCHEDULED:
	            	break;
            }

        	String[] imgData = Part.findPartImage(part);
        	String imgPath = imgData[0] + imgData[1] + imgMod + ".png";
            
            Image imgTool = getToolkit().getImage(imgPath); //$NON-NLS-1$
            
            this.setImage(imgTool);
            
            return c;
        }
    }
}