/*
 * Copyright (C) 2004-2013 L2J DataPack
 *
 * This file is part of L2J DataPack.
 *
 * L2J DataPack is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * L2J DataPack is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package custom.NPCBufferFull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.l2jserver.L2DatabaseFactory;
import com.l2jserver.gameserver.communitybbs.Managers.BuffBBSManager;
import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.olympiad.OlympiadManager;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.network.serverpackets.ActionFailed;
 
/**
 * @author Colet (Phynton)
 * @author Marcoviny (Translate for java)
 */
public class NPCBufferFull extends Quest
{
   public static final Logger _log = Logger.getLogger(NPCBufferFull.class.getName());
   
   // NPC Id
   static int bufferNpc = 40015;
   
   // Noble Items
   static int bufferItemId = 57;
   static long bufferItemCount = 100000;
    
   public NPCBufferFull(int questId, String name, String descr)
   {
        super(questId, name, descr);

        addStartNpc(bufferNpc);
        addFirstTalkId(bufferNpc);
        addTalkId(bufferNpc);
    }
   
    public static void main(String[] args)
    {
        new NPCBufferFull(-1, NPCBufferFull.class.getSimpleName(), "custom");
    }
    
    @Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
    {
        if (player.getQuestState(getName())== null)
        {
            newQuestState(player);
        }
        return "1.htm";
    }
    
	//@formatter:off
	static int[][] buffsMagicalAtk = {
		{264,230},{265,230},{273,230},{276,230},{304,230},{305,230},{349,1},{363,1},{364,1},{365,1},{529,1},{915,1},
		{830,1},{1048,230},{1033,330},{1035,230},{1036,230},{1040,230},{1044,230},{1045,230},{1059,3},{1062,230},{1078,230},
		{1085,230},{1182,330},{1189,330},{1191,330},{1259,330},{1268,4},{1284,230},{1303,230},{1362,215},{1389,130},
		{1392,130},{1397,330},{1413,215},{1442,130},{1461,215},{1548,330},{1204,230},{1232,330},{4703,13},{1323,1}
	};
	static int[][] buffsPhysicalAtk = {
		{264,230},{265,230},{266,230},{269,230},{271,230},{272,230},{274,230},{275,230},{304,230},{305,230},{529,1},
		{915,1},{829,1},{1033,330},{1035,230},{1036,230},{1040,230},{1044,230},{1045,230},{1062,230},{1068,230},{1077,230},
		{1086,230},{1182,330},{1189,330},{1191,330},{1242,230},{1252,230},{1259,330},{1310,230},{1362,215},{1363,315},
		{1388,130},{1392,130},{1442,130},{1461,215},{1548,330},{1204,230},{1232,330},{4699,1},{1323,1},{ 988, 3}
	};
	static int[][] buffsMagicalDef = {
		{123,230},{264,230},{265,230},{266,230},{267,230},{272,230},{277,230},{304,230},{305,230},{349,1},{364,1},
		{529,1},{830,1},{1033,330},{1035,230},{1040,230},{1044,230},{1045,230},{1048,230},{1078,230},{1182,330},{1189,330},
		{1191,330},{1259,330},{1357,315},{1362,215},{1391,130},{1392,130},{1416,115},{1442,130},{1461,215},{1542,1},
		{1548,330},{1204,230},{1232,330},{1323,1},{ 989, 1}
	};
	static int[][] buffsPhysicalDef = {
		{264,230},{265,230},{266,230},{267,230},{272,230},{277,230},{304,230},{305,230},{349,1},{364,1},{529,1},{829,1},
		{1033,330},{1035,230},{1036,230},{1040,230},{1044,230},{1045,230},{1048,230},{1182,330},{1189,330},{1191,330},
		{1259,330},{1357,315},{1362,215},{1391,130},{1392,130},{1416,115},{1442,130},{1461,215},{1542,1},{1548,330},
		{1204,230},{1232,330},{1323,1},{ 989, 1}
	};
	static int[][] buffsMagicSpecial = {
		{264,230},{265,230},{266,230},{267,230},{268,230},{273,230},{276,230},{304,230},{305,230},{349,1},{363,1},{364,1},
		{365,1},{529,1},{915,1},{830,1},{1033,330},{1035,230},{1036,230},{1040,230},{1044,230},{1045,230},{1048,230},{1059,230},
		{1062,230},{1078,230},{1085,230},{1182,330},{1189,330},{1191,330},{1259,330},{1268,4},{1303,230},{1357,315},{1362,215},
		{1364,215},{1389,130},{1392,130},{1397,330},{1442,130},{1461,215},{1542,1},{1548,330},{1204,230},{1232,330},{4703,1},
		{1323,1},{ 914, 1}
	};
	static int[][] buffsPhysicalSpecial = {
		{264,230},{265,230},{266,230},{267,230},{268,230},{269,230},{271,230},{272,230},{274,230},{275,230},{277,230},{304,230},
		{305,230},{349,1},{364,1},{529,1},{915,1},{829,1},{1033,330},{1035,230},{1036,230},{1040,230},{1044,230},{1045,230},
		{1048,230},{1062,230},{1068,230},{1077,230},{1086,230},{1182,330},{1189,330},{1191,330},{1242,230},{1259,330},{1310,230},
		{1362,215},{1363,315},{1364,215},{1388,130},{1392,130},{1397,330},{1442,130},{1461,215},{1542,1},{1548,330},{1204,230},
		{1232,330},{4699,1},{1323,1},{ 914, 1}
	};	//@formatter:on
	
	protected void buffing(L2PcInstance player, int[][] buffs)
	{
		for (int[] buff : buffs)
		{
			SkillTable.getInstance().getInfo(buff[0], buff[1]).getEffects(player, player);
		}
		return;
	}
    
    @Override
 	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
    {
		String htmlText = event;
		QuestState st = player.getQuestState(getName());
		//player.sendMessage("Event: "+event); //MSG only for debug
		
		// Manager for Initial
        if (OlympiadManager.getInstance().isRegistered(player))
        {
        	return "NPCBuffer-Blocked.htm";
        }
        
        switch (event)
        {
    		//Wind Walk
    		case "2":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1204,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "3.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Decrease Weight
    		case "3":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1257,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "9.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Shield
    		case "4":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1040,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "3.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Might
    		case "5":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1068,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "3.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Mental Shield
    		case "6":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1035,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "3.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Bless the Body
    		case "7":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1045,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "3.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Bless the Soul
    		case "8":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1048,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "3.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Magic Barrier
    		case "9":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1036,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "3.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Resist Shock
    		case "10":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1259,330).getEffects(st.getPlayer(),st.getPlayer());
    				return "9.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Concentration
    		case "11":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1078,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "3.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Berserker Spirit
    		case "12":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1062,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "8.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Bless Shield
    		case "13":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1243,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "3.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Vampiric Rage
    		case "14":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1268,4).getEffects(st.getPlayer(),st.getPlayer());
    				return "5.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Acumen
    		case "15":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1085,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "3.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Empower
    		case "16":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1059,3).getEffects(st.getPlayer(),st.getPlayer());
    				return "5.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Haste
    		case "17":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1086,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "3.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Guidance
    		case "18":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1240,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "3.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Focus
    		case "19":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1077,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "3.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Death Whisper
    		case "20":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1242,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "3.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		case "21":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(271,230).getEffects(st.getPlayer(),st.getPlayer());	
    				return "2.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		case "22":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);	
    				SkillTable.getInstance().getInfo(272,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "2.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		case "23":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(273,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "2.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		case "24":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(274,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "2.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		case "25":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(275,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "2.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		case "26":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(276,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "2.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		case "27":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(277,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "2.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		case "28":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(307,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "2.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		case "29":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(309,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "2.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		case "30":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(310,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "2.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		case "31":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);		
    				SkillTable.getInstance().getInfo(311,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "2.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		case "32":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(366,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "2.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		case "33":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(365,1).getEffects(st.getPlayer(),st.getPlayer());			
    				return "2.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		case "34":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(264,230).getEffects(st.getPlayer(),st.getPlayer());	
    				return "4.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		case "35":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);	
    				SkillTable.getInstance().getInfo(265,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "4.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		case "36":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(266,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "4.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		case "37":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(267,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "4.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		case "38":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(268,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "4.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		case "39":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(269,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "4.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		case "40":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(270,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "4.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		case "41":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(304,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "4.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		case "42":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(305,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "4.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		case "43":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(306,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "4.htm";	
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		case "44":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);	
    				SkillTable.getInstance().getInfo(308,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "4.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		case "45":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(363,1).getEffects(st.getPlayer(),st.getPlayer());
    				return "4.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		case "46":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(364,1).getEffects(st.getPlayer(),st.getPlayer());
    				return "4.htm";	
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		case "47":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(349,1).getEffects(st.getPlayer(),st.getPlayer());
    				return "4.htm";	
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Chant of Battle
    		case "48":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1007,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "6.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Chant of Shielding
    		case "49":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1009,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "6.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Chant of Fire
    		case "50":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1006,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "6.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Chant of Flame
    		case "51":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1002,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "6.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Chant of life
    		case "52":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1229,18).getEffects(st.getPlayer(),st.getPlayer());
    				return "6.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Chant of Fury
    		case "53":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1251,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "6.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Chant of Evasion
    		case "54":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1252,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "6.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Chant of Rage
    		case "55":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1253,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "6.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Chant of Revenge
    		case "56":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1284,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "6.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Chant of Vampire
    		case "57":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1310,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "6.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Chant of Eagle
    		case "58":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1309,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "6.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Chant of Predator
    		case "59":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1308,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "6.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Chant of Spirit
    		case "60":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1362,215).getEffects(st.getPlayer(),st.getPlayer());
    				return "6.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Chant of Victory
    		case "61":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1363,315).getEffects(st.getPlayer(),st.getPlayer());
    				return "6.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//chant of magnus
    		case "62":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1413,215).getEffects(st.getPlayer(),st.getPlayer());
    				return "6.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}	
    		//MPreg
    		case "63":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1013,32).getEffects(st.getPlayer(),st.getPlayer());
    				return "1.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}			
    		//greatmight
    		case "64":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1388,130).getEffects(st.getPlayer(),st.getPlayer());
    				return "8.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//greatshield
    		case "65":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1389,130).getEffects(st.getPlayer(),st.getPlayer());
    				return "8.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//holyresist
    		case "66":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1392,130).getEffects(st.getPlayer(),st.getPlayer());
    				return "7.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Unholyresist
    		case "67":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1393,130).getEffects(st.getPlayer(),st.getPlayer());
    				return "7.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//pof
    		case "68":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1356,315).getEffects(st.getPlayer(),st.getPlayer());
    				return "8.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//elemtprotect
    		case "69":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1352,215).getEffects(st.getPlayer(),st.getPlayer());
    				return "7.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//wildmagic
    		case "70":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1303,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "5.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//advblock
    		case "71":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1304,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "9.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//divProtect
    		case "72":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1353,215).getEffects(st.getPlayer(),st.getPlayer());
    				return "9.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//pow
    		case "73":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1355,315).getEffects(st.getPlayer(),st.getPlayer());
    				return "9.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//powi
    		case "74":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1357,315).getEffects(st.getPlayer(),st.getPlayer());
    				return "5.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//manaregen
    		case "75":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1047,4).getEffects(st.getPlayer(),st.getPlayer());
    				return "2.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//nobless
    		case "76":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1323,1).getEffects(st.getPlayer(),st.getPlayer());
    				return "2.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Bless of Seraphim
    		case "77":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(4702,13).getEffects(st.getPlayer(),st.getPlayer());
    				return "10.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Gift of Seraphim
    		case "78":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(4703,13).getEffects(st.getPlayer(),st.getPlayer());
    				return "10.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//BoQ
    		case "79":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(4699,13).getEffects(st.getPlayer(),st.getPlayer());
    				return "10.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//GoQ
    		case "80":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(4700,13).getEffects(st.getPlayer(),st.getPlayer());
    				return "10.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		case "84":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(4094,12).getEffects(st.getPlayer(),st.getPlayer());
    				st.getPlayer().stopAllEffects();
    				return "1.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		case "85":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1323,1).getEffects(st.getPlayer(),st.getPlayer());
    				return "1.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		case "86":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(915,1).getEffects(st.getPlayer(),st.getPlayer());			
    				return "2.htm";	
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		case "87":
    		{
    			if (player.isInCombat()||(player.getPvpFlag() == 1))
    			{
    				return "NPCBuffer-Blocked.htm";
    			}
    			else if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				st.getPlayer().getStatus().setCurrentHp(st.getPlayer().getStat().getMaxHp());
    				st.getPlayer().getStatus().setCurrentMp(st.getPlayer().getStat().getMaxMp());
    				st.getPlayer().getStatus().setCurrentCp(st.getPlayer().getStat().getMaxCp());
    				return "1.htm";		
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Decrease Weight
    		case "88":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(530,1).getEffects(st.getPlayer(),st.getPlayer());
    				return "2.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Holy Weapon
    		case "89":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1043,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "3.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Regeneration
    		case "90":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1044,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "3.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Kiss of Eva
    		case "91":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1073,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "3.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Invigor
    		case "92":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1032,330).getEffects(st.getPlayer(),st.getPlayer());
    				return "3.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Song of elemental
    		case "93":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(529,1).getEffects(st.getPlayer(),st.getPlayer());
    				return "4.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Resist Wind
    		case "94":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1189,330).getEffects(st.getPlayer(),st.getPlayer());
    				return "7.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Decrease Weight
    		case "95":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1354,215).getEffects(st.getPlayer(),st.getPlayer());
    				return "5.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//War Chant
    		case "96":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1390,130).getEffects(st.getPlayer(),st.getPlayer());
    				return "6.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Earth Chant
    		case "97":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1391,130).getEffects(st.getPlayer(),st.getPlayer());
    				return "6.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Chant of Protection
    		case "98":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1461,215).getEffects(st.getPlayer(),st.getPlayer());
    				return "6.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Resist Fire 
    		case "99":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1191,330).getEffects(st.getPlayer(),st.getPlayer());
    				return "7.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Resist of poison
    		case "100":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1033,330).getEffects(st.getPlayer(),st.getPlayer());
    				return "7.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//resist of Agua
    		case "101":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1182,330).getEffects(st.getPlayer(),st.getPlayer());
    				return "7.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Agylit
    		case "102":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1087,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "9.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Clarity Weight
    		case "103":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1397,330).getEffects(st.getPlayer(),st.getPlayer());
    				return "9.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Mana Gain
    		case "104":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1460,215).getEffects(st.getPlayer(),st.getPlayer());
    				return "5.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Sharp Edge 
    		case "105":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(825,1).getEffects(st.getPlayer(),st.getPlayer());
    				return "10.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//spike
    		case "106":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(826,1).getEffects(st.getPlayer(),st.getPlayer());
    				return "10.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Restring
    		case "107":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(827,1).getEffects(st.getPlayer(),st.getPlayer());
    				return "10.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Case Harden
    		case "108":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(828,1).getEffects(st.getPlayer(),st.getPlayer());
    				return "10.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Hard Tunning
    		case "109":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(829,1).getEffects(st.getPlayer(),st.getPlayer());
    				return "10.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Embroider
    		case "110":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(830,1).getEffects(st.getPlayer(),st.getPlayer());
    				return "10.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Pa'agrian Gift 
    		case "111":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1003,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "11.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Wisdom of Pa'agrio
    		case "112":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1004,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "11.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Blessin. of Pa'agrio 
    		case "113":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1005,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "11.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Glory of Pa'agrio
    		case "114":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1008,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "11.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Vision of Pa'agrio 
    		case "115":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1249,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "11.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Protect. of Pa'agrio
    		case "116":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1250,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "11.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Tact of Pa'agrio
    		case "117":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1260,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "11.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Pa'grio Rage
    		case "118":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1261,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "11.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Pa'agrian Haste
    		case "119":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1282,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "11.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Eye of Pa'agrio 
    		case "120":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1364,215).getEffects(st.getPlayer(),st.getPlayer());
    				return "11.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Soul of Pa'agrio
    		case "121":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1365,215).getEffects(st.getPlayer(),st.getPlayer());
    				return "11.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Pa'agrio Victo
    		case "122":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1414,315).getEffects(st.getPlayer(),st.getPlayer());
    				return "11.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Pa'agrio's Emblem 
    		case "123":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1415,215).getEffects(st.getPlayer(),st.getPlayer());
    				return "11.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Pa'agrio's Fist
    		case "124":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1416,115).getEffects(st.getPlayer(),st.getPlayer());
    				return "11.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Improved Combat
    		case "125":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1499,13).getEffects(st.getPlayer(),st.getPlayer());
    				return "12.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Improved Magic
    		case "126":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1500,13).getEffects(st.getPlayer(),st.getPlayer());
    				return "12.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Decrease Weight
    		case "127":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1501,13).getEffects(st.getPlayer(),st.getPlayer());
    				return "12.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Decrease Weight
    		case "128":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1502,13).getEffects(st.getPlayer(),st.getPlayer());
    				return "12.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Decrease Weight
    		case "129":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1503,13).getEffects(st.getPlayer(),st.getPlayer());
    				return "12.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Decrease Weight
    		case "130":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1504,13).getEffects(st.getPlayer(),st.getPlayer());
    				return "12.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		// buffers kamael desativados
    		//Decrease Weight
    		case "131":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1476,1).getEffects(st.getPlayer(),st.getPlayer());
    				return "12.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Decrease Weight
    		case "132":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1477,1).getEffects(st.getPlayer(),st.getPlayer());
    				return "12.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Decrease Weight
    		case "133":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1478,1).getEffects(st.getPlayer(),st.getPlayer());
    				return "12.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Decrease Weight
    		case "134":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1479,1).getEffects(st.getPlayer(),st.getPlayer());
    				return "12.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Counter Critical
    		case "135":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1542,1).getEffects(st.getPlayer(),st.getPlayer());
    				return "3.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		// Magic Attack
    		case "81":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= (bufferItemCount*10))
    			{
    				st.takeItems(bufferItemId, (bufferItemCount*10));
    				buffing(st.getPlayer(), buffsMagicalAtk);
    				return "1.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		// Fighter Attack
    		case "82":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= (bufferItemCount*10))
    			{
    				st.takeItems(bufferItemId, (bufferItemCount*10));
    				buffing(st.getPlayer(), buffsPhysicalAtk);
    				return "1.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		// Magic Defense
    		case "157":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= (bufferItemCount*10))
    			{
    				st.takeItems(bufferItemId, (bufferItemCount*10));
    				buffing(st.getPlayer(), buffsMagicalDef);
    				return "1.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		// Fighter Defense
    		case "158":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= (bufferItemCount*10))
    			{
    				st.takeItems(bufferItemId, (bufferItemCount*10));
    				buffing(st.getPlayer(), buffsPhysicalDef);
    				return "1.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		// Mage Special
    		case "136":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= (bufferItemCount*10))
    			{
    				st.takeItems(bufferItemId, (bufferItemCount*10));
    				buffing(st.getPlayer(), buffsMagicSpecial);
    				return "1.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		// Fighter Special
    		case "137":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= (bufferItemCount*10))
    			{
    				st.takeItems(bufferItemId, (bufferItemCount*10));
    				buffing(st.getPlayer(), buffsPhysicalSpecial);
    				return "1.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Resist Earth 
    		case "138":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1548,330).getEffects(st.getPlayer(),st.getPlayer());
    				return "7.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Resist Dark 
    		case "139":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1442,130).getEffects(st.getPlayer(),st.getPlayer());
    				return "7.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Cozy Mucus 
    		case "140":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(6429,1).getEffects(st.getPlayer(),st.getPlayer());
    				return "13.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}				
    		//Combat Aura
    		case "141":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(982,3).getEffects(st.getPlayer(),st.getPlayer());
    				return "13.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Blazing Skin
    		case "142":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1232,330).getEffects(st.getPlayer(),st.getPlayer());
    				return "13.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Freezing Skin
    		case "143":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1238,330).getEffects(st.getPlayer(),st.getPlayer());
    				return "13.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Prayer
    		case "144":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1307,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "13.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Heroic Valor
    		case "147":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1374,1).getEffects(st.getPlayer(),st.getPlayer());
    				return "13.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Spirit Barrier
    		case "148":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(123,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "13.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		// Holy Weapon
    		case "149":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1043,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "13.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		// Dark Weapon
    		case "150":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1443,130).getEffects(st.getPlayer(),st.getPlayer());
    				return "13.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		// Fire Weapon
    		case "151":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1463,1).getEffects(st.getPlayer(),st.getPlayer());
    				return "13.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		// Ice Weapon
    		case "152":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1464,1).getEffects(st.getPlayer(),st.getPlayer());
    				return "13.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		// Wind Weapon
    		case "153":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1465,1).getEffects(st.getPlayer(),st.getPlayer());
    				return "13.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		// Earth Weapon
    		case "154":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1466,1).getEffects(st.getPlayer(),st.getPlayer());
    				return "13.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
        		
    		// ======================================================================================
    		// Scheme SAVE
    		case "155":
    		{
    			try (Connection con = L2DatabaseFactory.getInstance().getConnection();)
    			{
    				PreparedStatement stat = con.prepareStatement("SELECT COUNT(*) FROM community_skillsave WHERE charId=?;");
    				stat.setInt(1, st.getPlayer().getObjectId());
    				ResultSet rset = stat.executeQuery();
    				rset.next();
    				String allbuff = "";

    				L2Effect skill[] = st.getPlayer().getAllEffectsCB();
    				boolean flag = true;
    				int arr$[][] = BuffBBSManager.getInstance().allskillid_1;
    				int len$ = arr$.length;
    				for (int i$ = 0; i$ < len$; i$++)
    				{
    					int aSkillid[] = arr$[i$];
    					for (int j = 0; j < skill.length; j++)
    					{
    						if (aSkillid[0] == skill[j].getId())
    						{
    							allbuff = (new StringBuilder()).append(allbuff).append(1).toString();
    							flag = false;
    						}
    						if ((j == (skill.length - 1))&& flag)
    						{
    							allbuff = (new StringBuilder()).append(allbuff).append(0).toString();
    						}
    					}
    					flag = true;
    				}
    				
    				if (rset.getInt(1) == 0)
    				{
    					PreparedStatement statement1 = con.prepareStatement("INSERT INTO community_skillsave (charId,skills) values (?,?)");
    					statement1.setInt(1, st.getPlayer().getObjectId());
    					statement1.setString(2, allbuff);
    					statement1.execute();
    					statement1.close();
    				}
    				else
    				{
    					PreparedStatement statement = con.prepareStatement("UPDATE community_skillsave SET skills=? WHERE charId=?;");
    					statement.setString(1, allbuff);
    					statement.setInt(2, st.getPlayer().getObjectId());
    					statement.execute();
    					statement.close();
    				}
    				rset.close();
    				stat.close();
    			}
    			catch (SQLException e)
    			{
    				e.printStackTrace();
    			}
    			return "1.htm";
    		}
    		// Scheme USE
    		case "156":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				// st.takeItems(bufferItemId, bufferItemCount);
    				// try(Connection con = L2DatabaseFactory.getInstance().getConnection();
    				// PreparedStatement statement = con.prepareStatement("SELECT * FROM community_skillsave WHERE charId=?;");)
    				
    				try(Connection con = L2DatabaseFactory.getInstance().getConnection();
    				PreparedStatement statement = con.prepareStatement("SELECT skills FROM community_skillsave WHERE charId=?;");)
    				{
    					statement.setInt(1, st.getPlayer().getObjectId());
    					ResultSet rcln = statement.executeQuery();
    					// rcln.next();
    					
    					if (rcln.next() && rcln.getString(1) != null)
    					{
	    					// DEF char allskills[] = rcln.getString(2).toCharArray();
	    					// char allskills[] = rcln.getString("skills").toCharArray();
	    					
	    					char allskills[] = rcln.getString(1).toCharArray();
	    					if (allskills.length == BuffBBSManager.getInstance().allskillid_1.length)
	    					{
	    						for (int i = 0; i < BuffBBSManager.getInstance().allskillid_1.length; i++)
	    						{
	    							if (allskills[i] != '1')
	    							{
	    								continue;
	    							}
	    							
									int skilllevel = BuffBBSManager.getInstance().allskillid_1[i][4];
									L2Skill skill = SkillTable.getInstance().getInfo(BuffBBSManager.getInstance().allskillid_1[i][0], skilllevel);
									skill.getEffects(st.getPlayer(), st.getPlayer());
	    						}
	    					}
	    					st.takeItems(bufferItemId, bufferItemCount);
    					}
    					else
    					{
           					st.getPlayer().sendPacket(ActionFailed.STATIC_PACKET);
           					st.getPlayer().sendMessage("You Not Have Scheme");
           					return "1.htm";
    					}
    				}
    				catch (SQLException e)
    				{
    					e.printStackTrace();
    				}
    				return "1.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		// Chant of Blood Awakening
    		case "159":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(1519,1).getEffects(st.getPlayer(),st.getPlayer());
    				return "12.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Song of Wind Storm
    		case "160":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(764,230).getEffects(st.getPlayer(),st.getPlayer());
    				return "4.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Song of Purification
    		case "161":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(914,1).getEffects(st.getPlayer(),st.getPlayer());
    				return "4.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		//Song Battle Whispers
    		case "162":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);
    				SkillTable.getInstance().getInfo(988,1).getEffects(st.getPlayer(),st.getPlayer());
    				return "4.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		// Dance Defence Motion
    		case "163":
    		{
    			if (st.getQuestItemsCount(bufferItemId) >= bufferItemCount)
    			{
    				st.takeItems(bufferItemId, bufferItemCount);	
    				SkillTable.getInstance().getInfo(989,3).getEffects(st.getPlayer(),st.getPlayer());
    				return "2.htm";
    			}
    			return "NPCBuffer-NoItems.htm";
    		}
    		default:
    			break;
        }
        
        return htmlText;
 	}
}