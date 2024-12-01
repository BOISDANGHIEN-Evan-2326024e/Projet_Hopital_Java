package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Colonie {

    private List<Meute> listeMeutes; 
    private Random random; 
    
    public Colonie() {
        this.listeMeutes = new ArrayList<>();
        this.random = new Random();
    }


    public void ajouterMeute(Meute meute) {
        listeMeutes.add(meute);
    }

    public void afficherLycanthropes() {
        System.out.println("===== Lycanthropes dans la colonie =====");
        for (Meute meute : listeMeutes) {
            System.out.println("Meute :");
            meute.afficherCaracteristiques();
        }
    }


    public void gestionColonie() {
        System.out.println("Début de la simulation de la colonie...");
        Scanner scanner = new Scanner(System.in);
        int compteurJour=1;
        while (true) { 
            try {
            	
            	Thread.sleep(10000); 

            	
            	System.out.println("Nous sommes au jour "+compteurJour+" !");
            	Thread.sleep(1000);
            	System.out.println("Il y a actuellement "+this.listeMeutes.size()+" meute/s, Voulez vous en créer une nouvelle ? Tapez 'meute' si oui.");
            	String commande2 = scanner.nextLine();
            	if(commande2.equals("meute")) {
            		System.out.println("Donnez le nom de la meute ?");
                	String commandeee = scanner.nextLine();
            		creerNouvelleMeute(commandeee);
            	}
            	System.out.println("Voulez vous créer un lycanthrope ? si oui tapez 'lycan'. Si non tapez 'non'.");
            	String commandeee1 = scanner.nextLine();
            	while(!commandeee1.equals("non")) {
            		if(commandeee1.equals("lycan")) {
                		System.out.println("Donnez le nom du lycan");
                		String commandeee2 = scanner.nextLine();
                		Lycanthrope lycan;
                		if(random.nextInt(3)==1) {
                			lycan=new Lycanthrope("femelle",random.nextInt(5), 0, random.nextInt(10), random.nextInt(4)+1, commandeee2);
                		}
                		else {
                			lycan=new Lycanthrope("male",random.nextInt(5), 0, random.nextInt(10), random.nextInt(4)+1, commandeee2);
                		}
                		lycan.afficherCaracteristiques();
                		System.out.println("Voulez vous associez le lycanthrope a la meute ? Tapez 'oui' si oui.");
                    	String commandeee3 = scanner.nextLine();
                    	if(commandeee3.equals("oui")){
                    		System.out.println("Voici les meutes disponibles :");
                    		for(int k=0;k<this.listeMeutes.size();k++) {
                            	System.out.println(listeMeutes.get(k).getNomMeute());
                            }
                    		System.out.println("Tapez le nom de la meute a qui vous souhaitez l'associé.");
                    		String commandeee4 = scanner.nextLine();
                    		for(int k=0;k<this.listeMeutes.size();k++) {
                            	if(listeMeutes.get(k).getNomMeute().equals(commandeee4)) {
                            		listeMeutes.get(k).ajouterLycanthrope(lycan);
                            		lycan.setMeute(listeMeutes.get(k));
                            	}
                            }
                    	}
                	}
            		else {
            			System.out.println("Commande inconnue.");
            		}
            		System.out.println("Voulez vous créer un autre lycanthrope ? si oui tapez 'lycan' si non tapez 'non'");
                	commandeee1 = scanner.nextLine();
            	}

            	for(int k=0;k<this.listeMeutes.size();k++) {
            		if(this.listeMeutes.get(k).coupleAlphaExiste()==false) {
            			Meute meuteEnCours=this.listeMeutes.get(k);
            			Lycanthrope maleAlpha=null;
            			Lycanthrope femelleAlpha=null;
            			for(int i=0;i<meuteEnCours.getLycanthropes().size();i++) {
            				Lycanthrope lycan=meuteEnCours.getLycanthropes().get(i);
            				if(lycan.getSexe().equals("femelle")) {
            					if(femelleAlpha==null) {
            						femelleAlpha=lycan;
            					}
            					if(lycan.getRang()>femelleAlpha.getRang()) {
            						femelleAlpha=lycan;
            					}
            				}
            				else {
            					if(maleAlpha==null) {
            						maleAlpha=lycan;
            					}
            					if(lycan.getRang()>maleAlpha.getRang()) {
            						maleAlpha=lycan;
            					}
            				}
            			}
            			this.listeMeutes.get(k).constituerCoupleAlpha(maleAlpha, femelleAlpha);;
            		}
            	}
            	
                Thread.sleep(1000);
                
                if (estSaisonAmours()) {
                    lancerReproduction();
                }

                Thread.sleep(1000);
                
                evoluerHierarchieMeutes();

                Thread.sleep(1000);
                
                faireVieillirLycanthropes();

                Thread.sleep(1000);
                
                genererHurlementsAleatoires();

                Thread.sleep(1000);
                
                transformerLycanthropes();
                
                Thread.sleep(1000);
                
                System.out.println("Si vous voulez consulter une meute tapez 'C', sinon tapez 'Suite");
                String commande = scanner.nextLine();
                
                while(!commande.equals("Suite")) {
                	if(commande.equals("C")) {
                        System.out.println("Tapez le nom de la meute que vous souhaitez consulter :");
                        for(int k=0;k<this.listeMeutes.size();k++) {
                        	System.out.println(listeMeutes.get(k).getNomMeute());
                        }
                        String nom = scanner.nextLine();
                        for(int k=0;k<this.listeMeutes.size();k++) {
                        	if(listeMeutes.get(k).getNomMeute().equals(nom)) {
                        		Meute meuteEnCours=listeMeutes.get(k);
                        		System.out.println("Si vous souhaitez consulter les statistiques de la meute, tapez 'C'. Si vous voulez consulter les caracteristiques d'un loup tapez 'L'. Pour faire retour tapez 'R' ");
                        		String nom1 = scanner.nextLine();
                        		if(nom1.equals("C")) {
                        			meuteEnCours.afficherCaracteristiques();
                        		}
                        		if(nom1.equals("L")) {
                        			System.out.println("Quel loup souhaitez vous consulter ses caractéristiques ?");
                        			for(int i=0;i<meuteEnCours.getLycanthropes().size();i++) {
                        				System.out.println(meuteEnCours.getLycanthropes().get(i).getNom());
                        			}
                        			String commande23 = scanner.nextLine();
                        			for(int i=0;i<meuteEnCours.getLycanthropes().size();i++) {
                        				if(meuteEnCours.getLycanthropes().get(i).getNom().equals(commande23)) {
                        					System.out.println("===== Caractéristiques du lycanthrope =====");
                        					meuteEnCours.getLycanthropes().get(i).afficherCaracteristiques();
                        				}
                        			}
                        		}
                        		if(nom1.equals("R")) {
                        			continue;
                        		}
                        		else {
                        			System.out.println("Commande inconnue");
                        		}
                        	}
                        }
                     }
                	else {
                		System.out.print("Commande inconnue");
                	}
                	System.out.println("Si vous voulez consulter une meute tapez 'C', sinon tapez 'Suite");
                    commande = scanner.nextLine();
                }
                compteurJour+=1;
                System.out.println("Nous passons au jour d'aprés.");
            } catch (InterruptedException e) {
                System.out.println("Simulation interrompue.");
                break;
            }
        }
    }

    // Déterminer si une nouvelle meute doit être créée
    private boolean doitCreerNouvelleMeute() {
        for (Meute meute : listeMeutes) {
            if (meute.getLycanthropes().size() > 12) {
                return true;
            }
        }
        if(listeMeutes.size()==0) {
        	return true;
        }
        return false;
    }

    // Créer une nouvelle meute
    private void creerNouvelleMeute(String nomMeute) {
        Meute nouvelleMeute = new Meute(nomMeute);
        System.out.println("Une nouvelle meute a été créée !");
        listeMeutes.add(nouvelleMeute);
    }

    // Déterminer si c'est la saison des amours
    private boolean estSaisonAmours() {
        // 10% de chance que ce soit la saison des amours
        return random.nextInt(100) < 10;
    }

    // Lancer la reproduction dans toutes les meutes
    private void lancerReproduction() {
        System.out.println("C'est la saison des amours !");
        for (Meute meute : listeMeutes) {
            meute.reproduction();
        }
    }

    // Faire évoluer la hiérarchie des meutes
    private void evoluerHierarchieMeutes() {
        System.out.println("Évolution naturelle des hiérarchies dans les meutes...");
        for (Meute meute : listeMeutes) {
                meute.decroitreRangsNaturellement();
        }
    }

    // Faire vieillir certains lycanthropes
    private void faireVieillirLycanthropes() {
        System.out.println("Les lycanthropes vieillissent...");
        for (Meute meute : listeMeutes) {
            for (Lycanthrope lycan : meute.getLycanthropes()) {
            	if(random.nextInt(100) < 20) {
                	if(lycan.getCategorieAge()=="adulte") {
                		lycan.setCategorieAge("vieux");
                	}
                	if(lycan.getCategorieAge()=="jeune") {
                		lycan.setCategorieAge("adulte");
                	}
                	else {
                		continue;
                	}
            	}
            	
            }
        }
    }

    // Générer des hurlements aléatoires entre certains lycanthropes
    private void genererHurlementsAleatoires() {
        System.out.println("Des hurlements aléatoires résonnent dans la colonie...");
        for (Meute meute : listeMeutes) {
            List<Lycanthrope> lycanthropes = meute.getLycanthropes();
            if (lycanthropes.size() > 1) {
                Lycanthrope emetteur = lycanthropes.get(random.nextInt(lycanthropes.size()));
                Lycanthrope recepteur = lycanthropes.get(random.nextInt(lycanthropes.size()));
                if (!emetteur.equals(recepteur)) {
                    Hurlement hurlement = new Hurlement("domination", emetteur);
                    emetteur.hurler(hurlement);
                    recepteur.entendreHurlement(hurlement, false);// A MODIFIER
                }
            }
        }
    }

    // Transformer quelques lycanthropes en humains
    private void transformerLycanthropes() {
        System.out.println("Quelques lycanthropes se transforment en humains...");
        for (Meute meute : listeMeutes) {
            for (Lycanthrope lycan : meute.getLycanthropes()) {
                if (random.nextInt(100) < 5) { // 5 % de chance de transformation
                    lycan.transformation();
                    System.out.println(lycan.getNom()+" se transforme !");
                    meute.enleverLycanthrope(lycan);
                }
            }
        }
    }
}

