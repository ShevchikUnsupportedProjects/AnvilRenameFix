/**
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * 
 */

package anvilrenamefix;

import com.comphenix.protocol.Packets;
import com.comphenix.protocol.events.ConnectionSide;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

public class PacketListener {

	private AnvilRenameFixPlugin pluginInstance;
	private AnvilNameDecoder nameDecoder;
	public PacketListener(AnvilRenameFixPlugin plugin, AnvilNameDecoder nameDecoder)
	{
		this.pluginInstance = plugin;
		this.nameDecoder = nameDecoder;
		initAnvilCustomPacketListener();
	}
	
	
	private void initAnvilCustomPacketListener()
	{
		pluginInstance.getProtocolManager().addPacketListener(
				  new PacketAdapter(pluginInstance, ConnectionSide.CLIENT_SIDE, 
				  ListenerPriority.HIGHEST, Packets.Client.CUSTOM_PAYLOAD) {
					@Override
				    public void onPacketReceiving(PacketEvent e) {
						PacketContainer packet = e.getPacket();
						//check if it is anvil rename packet
						if (packet.getStrings().getValues().get(0).equals("MC|ItemName"))
						{
							byte[] barray = packet.getByteArrays().getValues().get(0);
							if (barray != null)
							{
								pluginInstance.setDecodedItemStirng(e.getPlayer().getName(), nameDecoder.decodeName(barray));
							}
						}
					}
				});
	}
	
}
