import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

public class SocialNetwork
	{
		public static void main(String[] args)
			{
				System.out.println("Welcome to the social network!");
				System.out.println("All the inputs given are case-sensitive!");
				System.out.println("Enter Individual to create individual nodes.\nEnter Business to create business nodes.\nEnter Organization to create organization nodes.\nEnter Group to create group nodes.\nEnter QUIT to stop creating nodes. ");

				Vector<Nodes> nodes = new Vector<Nodes>();
				Scanner obj = new Scanner(System.in);
				String choiceString = obj.nextLine();
				int i = 0;
				Map<Integer, Nodes> mp;
				Map<Integer, String> mp2;
				mp = new HashMap<>();
				mp2 = new HashMap<>();
				Set<Integer> st;
				st = new HashSet<>();
				while (!choiceString.equals("QUIT"))
					{
						Nodes newNode = createNode(obj, choiceString, mp2);
						if (newNode != null)
							{
								nodes.add(newNode);
								mp.put(newNode.uniqueId, newNode);
//								
							} else
							{
								System.out.println("Wrong Input! Try again");

							}
						System.out.println("Enter Individual to create individual nodes.\nEnter Business to create business nodes.\nEnter Organization to create organization nodes.\nEnter Group to create group nodes.\nEnter QUIT to stop creating nodes. ");

						choiceString = obj.nextLine();
						i++;
					}
//				
				System.out.println("Four types of links are allowed\n1. Individual<->Business\n2.Individual<->Group\n3.Individual<->Group\n4.Business<->Group.\nAll the links established are bi-directional");
				System.out.println();
				System.out.println("Here 1st unique id represents the first node to be linked to the node with 2nd unique id");
				System.out.println("Do you want to add links?Type YES to do so otherwise NO");

				String addLinksChoiceString = obj.nextLine();
				while (addLinksChoiceString.equals("YES"))
					{

						System.out.println("Enter the 1st unique id which you want to link");
						int uNode = obj.nextInt();
						obj.nextLine();
						System.out.println("Enter the 2nd unique id which you want to link");
						int vNode = obj.nextInt();
						obj.nextLine();
						boolean uNodepresent = mp.containsKey(uNode);
						boolean vNodepresent = mp.containsKey(vNode);
						if (uNodepresent && vNodepresent)
							{
								addLinks(obj, uNode, vNode, mp);

							} else
							{
								System.out.println("The unique ID entered is not present .Try again!");

							}
						System.out.println("Do you want to add links?Type YES to do so otherwise NO");
						addLinksChoiceString = obj.nextLine();

					}
				System.out.println("Congratulations!The social network is established!!");
				System.out.println("Enter 1 to search for nodes using name ,type or birthday\nEnter 2 to print all the nodes linked to a particluar node\nEnter 3 to create content and post content\nEnter 4 to search for content posted by any node\nEnter 5 to display all content posted by nodes linked to a given node\nEnter 6 to display all nodes\nEnter 7 to delete a node\n");
				System.out.println("-------------------------------------------------------");
				System.out.println("Enter your choice and -1 to exit this social network");
				int choiceOfUser = obj.nextInt();
				obj.nextLine();
				while (choiceOfUser != -1)
					{
						if (choiceOfUser == 1)
							{
								searchNodes(nodes, mp2);

							} else if (choiceOfUser == 2)
							{
								st.clear();
								printLinkedNodes(obj, nodes, mp, st);

							} else if (choiceOfUser == 3)
							{
								createContent(obj, nodes, mp);
							} else if (choiceOfUser == 4)
							{
								searchPostContent(obj, nodes, mp);
							} else if (choiceOfUser == 5)
							{
								st.clear();
								displayAllNodesPosts(obj, nodes, mp, st);

							} else if (choiceOfUser == 6)
							{
								displayAllNodes(nodes, mp);

							} else if (choiceOfUser == 7)
							{
								deleteNode(obj, nodes, mp);

							} else
							{
								System.out.println("Wrong Input!Try again.");
							}
						System.out.println("Enter your choice and -1 to exit this social network");
						choiceOfUser = obj.nextInt();
						if (choiceOfUser == -1)
							{
								System.out.println("Thank You for participating with us!!");
								break;
							}
						obj.nextLine();
					}

			}

		public static Nodes createNode(Scanner obj, String typeString, Map<Integer, String> mp2)
			{

				if (typeString.equals("Individual"))
					{

						Individuals newnode = new Individuals(obj);
						mp2.put(newnode.uniqueId, newnode.birthDay);
						return newnode;

					}

				if (typeString.equals("Business"))
					return new Businesses(obj);
				if (typeString.equals("Organization"))
					return new Organizations(obj);
				if (typeString.equals("Group"))
					return new Groups(obj);
				return null;
			}

		public static void addLinks(Scanner obj, int uNodeID, int vNodeID, Map<Integer, Nodes> mp)
			{
				Nodes uNodes = mp.get(uNodeID);
				Nodes vNodes = mp.get(vNodeID);
				String uType = uNodes.nodeType;
				String vType = vNodes.nodeType;
				if (uType.equals("Individual"))
					{
						if (vType.equals("Individual"))
							{
								System.out.println("This connection does not exist try again!");
								return;
							}

						uNodes.linksNodes.add(vNodes);
						vNodes.linksNodes.add(uNodes);
						System.out.println("Connection successfully established!");

					} else if (vType.equals("Individual"))
					{
						if (uType.equals("Individual"))
							{
								System.out.println("This connection does not exist try again!");
								return;
							}
						uNodes.linksNodes.add(vNodes);
						vNodes.linksNodes.add(uNodes);
						System.out.println("Connection successfully established!");

					} else if (uType.equals("Business"))
					{
						if (vType.equals("Group"))
							{
								uNodes.linksNodes.add(vNodes);
								vNodes.linksNodes.add(uNodes);
								System.out.println("Connection successfully established!");

							} else
							{
								System.out.println("This connection does not exist try again!");
								return;

							}
					} else if (vType.equals("Business"))
					{
						if (uType.equals("Group"))
							{
								uNodes.linksNodes.add(vNodes);
								vNodes.linksNodes.add(uNodes);
								System.out.println("Connection successfully established!");

							} else
							{
								System.out.println("This connection does not exist try again!");
								return;
							}

					} else
					{
						System.out.println("This connection does not exist try again!");

					}
				return;
//				
			}

		public static void searchNodes(Vector<Nodes> nodes, Map<Integer, String> mp)
			{
				System.out.println("Enter A to search by name ,B to search by type C to search by birthday");
				Scanner sc = new Scanner(System.in);
				String searchString = sc.nextLine();
				if (searchString.equals("A"))
					{
						System.out.println("Enter the name to be searched");
						String nameSearchString = sc.nextLine();
						boolean flag = false;
						for (Nodes nn : nodes)
							{
								String nameString = nn.name;
								if (nameString.equals(nameSearchString))
									{

										System.out.println("Node Type: " + nn.nodeType + "\nUnique ID:" + nn.uniqueId + "\nName:" + nn.name + "\nCreation Date " + nn.currentDate + "\n");
										System.out.println();
										flag = true;

									}
							}
						if (!flag)
							System.out.println("Search Unsuccessful!The entered name does not exist.2");
					} else if (searchString.equals("B"))
					{
						System.out.println("Enter the type to be searched");
						String typeSearchString = sc.nextLine();
						boolean flag = false;
						for (Nodes nn : nodes)
							{
								String typeString = nn.nodeType;
								if (typeString.equals(typeSearchString))
									{

										// nn.print();
										System.out.println("Node Type: " + nn.nodeType + "\nUnique ID:" + nn.uniqueId + "\nName:" + nn.name + "\nCurrent Date" + nn.currentDate + "\n");
										flag = true;
										// break;
									}
							}
						if (!flag)
							System.out.println("Search Unsuccessful!The entered type does not exist.");

					} else if (searchString.equals("C"))
					{
						System.out.println("Enter the birthday to be searched");
						String birthSearchString = sc.nextLine();
						boolean flag = false;
						for (Nodes nn : nodes)
							{
								if (!nn.nodeType.equals("Individual"))
									continue;

								boolean presentID = mp.containsKey(nn.uniqueId);
								if (!presentID)
									{
										System.out.println("The required node does not exist");
										continue;
									}
								String birthString = mp.get(nn.uniqueId);
								if (birthString.equals(birthSearchString))
									{
										System.out.println("Node Type: " + nn.nodeType + "\nUnique ID:" + nn.uniqueId + "\nName:" + nn.name + "\nCurrent Date" + nn.currentDate + "\n");
										// nn.print();
										flag = true;
										// break;
									}
							}
						if (!flag)
							System.out.println("Search Unsuccessful!The entered birthday does not exist");

					}

			}

		public static void depthFirstSearch(Nodes vertex, Vector<Nodes> nodes, Map<Integer, Nodes> mp, Set<Integer> st, int choice)
			{
				st.add(vertex.uniqueId);
				if (choice == 1)
					{
						System.out.println("Node Type: " + vertex.nodeType + "\nUnique ID:" + vertex.uniqueId + "\nName:" + vertex.name + "\nCurrent Date" + vertex.currentDate + "\n");

					}
				// vertex.print();
				else
					{
						for (String pcString : vertex.postedContent)
							System.out.println("Posted Content by user with unique ID:" + vertex.uniqueId + "  is " + pcString);
					}
				for (Nodes nn : vertex.linksNodes)
					{
						if (st.contains(nn.uniqueId))
							continue;
						depthFirstSearch(nn, nodes, mp, st, choice);

					}
			}

		public static void printLinkedNodes(Scanner obj, Vector<Nodes> nodes, Map<Integer, Nodes> mp, Set<Integer> st)
			{
				System.out.println("Enter the unique ID of the node whose links you want to display");
				int userChoiceUniqueID = obj.nextInt();
				obj.nextLine();
				boolean presentUniqueID = mp.containsKey(userChoiceUniqueID);
				if (presentUniqueID)
					{
						Nodes userNodes = mp.get(userChoiceUniqueID);

						depthFirstSearch(userNodes, nodes, mp, st, 1);

					} else
					{
						System.out.println("The entered unique ID doesnt exist!");
					}
//

			}

		public static void createContent(Scanner obj, Vector<Nodes> nodes, Map<Integer, Nodes> mp)
			{
				System.out.println("Enter the unique ID of the user whose content is to be created");
				int userUniqueID = obj.nextInt();
				obj.nextLine();
				boolean presentID = mp.containsKey(userUniqueID);
				if (!presentID)
					{
						System.out.println("The user unique ID doesnt exist!");
						return;
					}
				Nodes userNode = mp.get(userUniqueID);
				System.out.println("Enter 1 to continue creating content and 0 to stop creating");
				int userChoice = obj.nextInt();
				obj.nextLine();
				while (userChoice != 0)
					{
						System.out.println("Enter the content to be created");
						String newContent = obj.nextLine();
						userNode.createdContent.add(newContent);
						System.out.println("Do you want to post this content?:(YES or NO)");
						String userChoicePost = obj.nextLine();
						if (userChoicePost.equals("YES"))
							{
								userNode.postedContent.add(newContent);
								System.out.println("User Unique ID:" + userNode.uniqueId + "   Posted Content:" + newContent);
							}
						System.out.println("Enter 1 to continue creating content and 0 to stop creating");
						userChoice = obj.nextInt();
						obj.nextLine();

					}

			}

		public static void searchPostContent(Scanner obj, Vector<Nodes> nodes, Map<Integer, Nodes> mp)
			{
				System.out.println("Enter the unique ID of the user whose post content is to be searched");
				int userUniqueID = obj.nextInt();
				obj.nextLine();
				boolean presentID = mp.containsKey(userUniqueID);
				if (!presentID)
					{
						System.out.println("The user unique ID doesnt exist!");
						return;
					}
				Nodes userNode = mp.get(userUniqueID);
				int i = 1;
				if (userNode.postedContent.size() == 0)
					System.out.println("The user with unique ID " + userUniqueID + "  has not posted any content");
				for (String pcString : userNode.postedContent)
					{

						System.out.println("Posted Content " + i + " by user with unique ID:" + userUniqueID + "  is " + pcString);
						i++;
					}

			}

		public static void displayAllNodesPosts(Scanner obj, Vector<Nodes> nodes, Map<Integer, Nodes> mp, Set<Integer> st)
			{
				System.out.println("Enter the unique ID of the user whose post content is to be searched");
				int userUniqueID = obj.nextInt();
				obj.nextLine();
				boolean presentID = mp.containsKey(userUniqueID);
				if (!presentID)
					{
						System.out.println("The user unique ID doesnt exist!");
						return;
					}
				Nodes userNode = mp.get(userUniqueID);
				int i = 0;
				if (userNode.linksNodes.size() == 0)
					{
						System.out.println("The entered node has no linked nodes and hence no posts-!");
						return;
					}
//				
				depthFirstSearch(userNode, nodes, mp, st, 2);

			}

		public static void displayAllNodes(Vector<Nodes> nodes, Map<Integer, Nodes> mp)
			{
				int i = 0;
				if (nodes.size() == 0)
					System.out.println("There are no nodes present in the network!");
				for (Nodes nn : nodes)
					{
						System.out.println("Node Type: " + nn.nodeType + "\nUnique ID:" + nn.uniqueId + "\nName:" + nn.name + "\nCurrent Date" + nn.currentDate + "\n");
						if (nn.linksNodes.size() == 0)
							{
								System.out.println("There are no nodes linked with node with uniqueID:" + nn.uniqueId);
								continue;
							}
						System.out.println("Nodes linked to node with unique ID:" + nn.uniqueId + " are ");
						i = 1;
						for (Nodes nodeLinks : nn.linksNodes)
							{
								System.out.println("The " + i + "th  neighbouring node with Unique ID:" + nodeLinks.uniqueId);
								i++;

							}
						System.out.println();
					}

			}

		public static void deleteNode(Scanner obj, Vector<Nodes> nodes, Map<Integer, Nodes> mp)
			{
//				
				System.out.println("Enter the unique ID of the node you want to delete");
				int userUniqueID = obj.nextInt();
				obj.nextLine();

				boolean presentID = mp.containsKey(userUniqueID);
				if (!presentID)
					System.out.println("The  user unique ID doesnt exist!Try again.");
				else
					{
						// Nodes userNodes = (Nodes) mp.get(userUniqueID);
						int j = 0;
						for (Nodes nn : nodes)
							{
								if (nn.uniqueId == userUniqueID)
									{
										break;
									}
								j++;
							}
						mp.remove(userUniqueID);
						if (j < nodes.size())
							{
								nodes.remove(j);
								for (Nodes nn : nodes)
									{

										for (int i = 0; i < nn.linksNodes.size(); i++)
											{
												Nodes newnodeNodes = nn.linksNodes.get(i);
												if (newnodeNodes.uniqueId == userUniqueID)
													{
														nn.linksNodes.remove(i);
													}

											}

									}
							}
						System.out.println("The node has been successfully deleted.");

					}

			}

	}

class Nodes
	{
		int uniqueId;
		Vector<Nodes> linksNodes;
		String name;
		String currentDate;
		Vector<String> postedContent;
		Set<String> createdContent;
		String nodeType;

		public Nodes(Scanner obj)
			{
				linksNodes = new Vector<Nodes>();
				postedContent = new Vector<String>();
				createdContent = new HashSet<>();
				Date date = new Date();
				this.currentDate = date.toString();
				this.nodeType = "";

				System.out.println("Enter the unique id: ");
				this.uniqueId = obj.nextInt();
				obj.nextLine();
				System.out.println("Enter name:");
				this.name = obj.nextLine();

			}

		public int getUniqueId()
			{
				return uniqueId;
			}

		public void setUniqueId(int uniqueId)
			{
				this.uniqueId = uniqueId;
			}

		public Vector<Nodes> getLinksNodes()
			{
				return linksNodes;
			}

		public void setLinksNodes(Vector<Nodes> linksNodes)
			{
				this.linksNodes = linksNodes;
			}

		public String getName()
			{
				return name;
			}

		public void setName(String name)
			{
				this.name = name;
			}

		public String getCurrentDate()
			{
				return currentDate;
			}

		public void setCurrentDate(String currentDate)
			{
				this.currentDate = currentDate;
			}

		public Vector<String> getPostContent()
			{
				return postedContent;
			}

		public void setPostContent(Vector<String> postedContent)
			{
				this.postedContent = postedContent;
			}

		void print()
			{
				System.out.println("Node Type: " + nodeType + "\nUnique ID:" + uniqueId + "\nName:" + name + "\nCurrent Date" + currentDate + "\n");
				System.out.println("Content Posted by this node:");
				for (String str : postedContent)
					System.out.println(str + "\n");
				System.out.println("\n");
				System.out.println("Neighbouring links of this node with unique ID:");
				for (Nodes obj : linksNodes)
					System.out.println(obj.uniqueId + "\n");
				System.out.println("\n");

			}

	}

class Individuals extends Nodes
	{
		String birthDay;

		public Individuals(Scanner obj)
			{
				super(obj);
				this.nodeType = "Individual";
				System.out.println("Enter birthday:");
				this.birthDay = obj.nextLine();

			}

		void addOrganization(Nodes obj)
			{
				linksNodes.add(obj);

			}

		void addGroup(Nodes obj)
			{
				linksNodes.add(obj);

			}

		void addBusiness(Nodes obj)
			{
				linksNodes.add(obj);

			}

		public String getBirthDay()
			{
				return birthDay;
			}

		public void setBirthDay(String birthDay)
			{
				this.birthDay = birthDay;
			}

	}

class Businesses extends Nodes
	{
		int coordinateX;
		int coordinateY;

		public Businesses(Scanner obj)
			{
				super(obj);
				this.nodeType = "Business";
				System.out.println("Enter the X-coordinate:");
				this.coordinateX = obj.nextInt();
				System.out.println("Enter the Y-coordinate:");
				this.coordinateY = obj.nextInt();
				String str = obj.nextLine();

			}

		void addIndividual(Nodes obj)
			{
				linksNodes.add(obj);
			}

		void addGroups(Nodes obj)
			{
				linksNodes.add(obj);
			}

		public int getCoordinateX()
			{
				return coordinateX;
			}

		public void setCoordinateX(int coordinateX)
			{
				this.coordinateX = coordinateX;
			}

		public int getCoordinateY()
			{
				return coordinateY;
			}

		public void setCoordinateY(int coordinateY)
			{
				this.coordinateY = coordinateY;
			}

	}

class Organizations extends Nodes
	{
		int coordinateX;
		int coordinateY;

		public Organizations(Scanner obj)
			{
				super(obj);
				this.nodeType = "Organization";
				System.out.println("Enter the X-coordinate:");
				this.coordinateX = obj.nextInt();
				System.out.println("Enter the Y-coordinate:");
				this.coordinateY = obj.nextInt();
				String str = obj.nextLine();

			}

		void addIndividual(Nodes obj)
			{
				linksNodes.add(obj);
			}

		public int getCoordinateX()
			{
				return coordinateX;
			}

		public void setCoordinateX(int coordinateX)
			{
				this.coordinateX = coordinateX;
			}

		public int getCoordinateY()
			{
				return coordinateY;
			}

		public void setCoordinateY(int coordinateY)
			{
				this.coordinateY = coordinateY;
			}

	}

class Groups extends Nodes
	{

		public Groups(Scanner obj)
			{
				super(obj);
				this.nodeType = "Group";

			}

		void addIndividual(Nodes obj)
			{
				linksNodes.add(obj);
			}

	}
