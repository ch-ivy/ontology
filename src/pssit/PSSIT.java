/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pssit;


import com.clarkparsia.pellet.owlapiv3.PelletReasoner;
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLMutableOntology;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;

/**
 *
 * @author DC
 */
public class PSSIT {

    /**
     * @param args the command line arguments
     */
    //ALLERGY.owl physical address on your computer
    public static final File Phy_Add_IRI = new File("ONTOLOGY/PSSITontology.owl");
    //ALLERGY.owl base address in your ontology file
    public static final IRI Onto_Base_IRI = IRI.create("http://securityontology/PSSIT");

    OWLOntologyManager m = OWLManager.createOWLOntologyManager();
    //Create an Ontology Factory object f 
    OWLDataFactory f = OWLManager.getOWLDataFactory();
    //Create an Ontology object o
    OWLOntology o = null;

    // Create a function that returns & prints all classes in ALLERGY.owl
    public ArrayList<String> AllClasses() {
        ArrayList<String> classes = new ArrayList<String>();
        try {
            o = m.loadOntologyFromOntologyDocument(Phy_Add_IRI);
            // These are the named classes referenced by axioms in the ontology.
            for (OWLClass cls : o.getClassesInSignature()) {
                // use the class for whatever purpose
                System.out.println("Class=" + cls.getIRI());
                classes.add(cls.getIRI().toString());
            }
        } catch (Exception e) {
            System.out.println("Could not create ontology: " + e.getMessage());
        }
        return classes;
    }

    // Create a function that returns & prints all OTP in ALLERGY.owl
    public ArrayList<String> AllOTP() {
        ArrayList<String> OTPs = new ArrayList<String>();
        try {
            o = m.loadOntologyFromOntologyDocument(Phy_Add_IRI);
            // These are the named classes referenced by axioms in the ontology.
            for (OWLObjectProperty otp : o.getObjectPropertiesInSignature()) {
                // use the class for whatever purpose
                System.out.println("OTP= " + otp.getIRI());
                OTPs.add(otp.getIRI().toString());
            }
        } catch (Exception e) {
            System.out.println("Could not create ontology: " + e.getMessage());
        }
        return OTPs;
    }
        // Create a function that returns & prints all DTP in ALLERGY.owl
    public ArrayList<String> AllDTP() {
        ArrayList<String> DTPs = new ArrayList<String>();
        try {
            o = m.loadOntologyFromOntologyDocument(Phy_Add_IRI);
            // These are the named DTPs referenced by axioms in the ontology.
            for (OWLDataProperty dtp : o.getDataPropertiesInSignature()) {
                // use the DTP for whatever purpose
                System.out.println("DTP= " + dtp.getIRI());
                DTPs.add(dtp.getIRI().toString());
            }
        } catch (Exception e) {
            System.out.println("Could not create ontology: " + e.getMessage());
        }
        return DTPs;
    }

    // Create a function that returns & prints all Individuals of a myClass in ALLERGY.owl
    public ArrayList<String> AllIndividualsOfmyClass(String myClass) {
        ArrayList<String> indlist = new ArrayList<String>();
        try {
            o = m.loadOntologyFromOntologyDocument(Phy_Add_IRI);
            IRI iri = IRI.create(Onto_Base_IRI + "#" + myClass);
            OWLClass cls = m.getOWLDataFactory().getOWLClass(iri);
            OWLReasonerFactory rf = new StructuralReasonerFactory();
            OWLReasoner r = rf.createReasoner(o);
            NodeSet<OWLNamedIndividual> instances = r.getInstances(cls, true);
            System.out.println("The Individuals of myClass : ");
            for (OWLNamedIndividual i : instances.getFlattened()) {
                String s = i.getIRI().getFragment();
                indlist.add(s);
            }
            System.out.println(indlist);
        } catch (Exception e) {
            System.out.println("Could not create ontology: " + e.getMessage());
        }
        return indlist;
    }

    // Create a function that returns all asserted axioms in ontology
    public ArrayList<String> AllAxioms() {
        ArrayList<String> AllAxioms = new ArrayList<String>();
        try {
            o = m.loadOntologyFromOntologyDocument(Phy_Add_IRI);
            for (OWLAxiom ax : o.getAxioms()) {
                AllAxioms.add(ax.toString());
                System.out.println(ax);
            }
        } catch (Exception e) {
            System.out.println("Could not create ontology: " + e.getMessage());
        }
        return AllAxioms;
    }

    // Create a function that returns all DataPropertyAssertion assigned to an Individual 
    public void AssertedDTPs_One_Individual(String myIndividual) {
        try {
            o = m.loadOntologyFromOntologyDocument(Phy_Add_IRI);
            OWLReasonerFactory rf = new StructuralReasonerFactory();
            OWLReasoner r = rf.createReasoner(o);
            //create an IRI for the input individual
            IRI iri = IRI.create(Onto_Base_IRI + "#" + myIndividual);
            OWLNamedIndividual indv = f.getOWLNamedIndividual(iri);
            // put the all DTPs inn ontology to a set
            Set<OWLDataProperty> DTPs = o.getDataPropertiesInSignature();
            //read every DTP asserted to that individual 
            for (OWLDataProperty Dprop : DTPs) {
                Set<OWLLiteral> DTP = r.getDataPropertyValues(indv, Dprop);
                System.out.println(indv.getIRI().getFragment() + "-->" + Dprop.getDataPropertiesInSignature() + "-->" + DTP);
                // or System.out.println(indv+"-->"+Dprop.getDataPropertiesInSignature()+"-->"+DTP);
            }
        } catch (Exception e) {
            System.out.println("Could not create ontology: " + e.getMessage());
        }
    }

    // Create a function that returns all ObjectPropertyAssertion assigned to an Individual 
    public void AssertedOTPs_One_Individual(String myIndividual) {
        try {
            o = m.loadOntologyFromOntologyDocument(Phy_Add_IRI);
            OWLReasonerFactory rf = new StructuralReasonerFactory();
            OWLReasoner r = rf.createReasoner(o);
            //create an IRI for the input individual
            IRI iri = IRI.create(Onto_Base_IRI + "#" + myIndividual);
            OWLNamedIndividual indv = f.getOWLNamedIndividual(iri);
            // put the all OTPs in ontology to a set
            Set<OWLObjectProperty> OTPs = o.getObjectPropertiesInSignature();
            //read every OTP asserted to that individual 
            for (OWLObjectProperty Oprop : OTPs) {
                Set<OWLNamedIndividual> OTP = r.getObjectPropertyValues(indv, Oprop).getFlattened();
                System.out.println(indv.getIRI().getFragment() + "-->" + Oprop.getIRI().getFragment() + "-->" + OTP);
                // or  System.out.println(indv + "-->" + Oprop+ "-->" + OTP);
            }
        } catch (Exception e) {
            System.out.println("Could not create ontology: " + e.getMessage());
        }
    }

    // Create a function that returns all AnnotationPropertyAssertion assigned to an Individual 
    public void AssertedATPs_One_Individual(String myIndividual) {
        try {
            o = m.loadOntologyFromOntologyDocument(Phy_Add_IRI);
            IRI iri = IRI.create(Onto_Base_IRI + "#" + myIndividual);
            OWLNamedIndividual indv = f.getOWLNamedIndividual(iri);
            Set<OWLAnnotation> annos = o.getAnnotations();
            for (OWLAnnotation annotation : annos) {
                System.out.println(indv + " labelled " + annotation.getValue());
            }
        } catch (Exception e) {
            System.out.println("Could not create ontology: " + e.getMessage());
        }
    } 
        public void saveClassAssertion( String className, String individual) {
        try {
            o = m.loadOntologyFromOntologyDocument(IRI.create(Phy_Add_IRI));
            OWLClass cust = f.getOWLClass(IRI.create(Onto_Base_IRI + "#" + className));
            OWLNamedIndividual tc = f.getOWLNamedIndividual(IRI.create(Onto_Base_IRI + "#" + individual));
            OWLClassAssertionAxiom ax = f.getOWLClassAssertionAxiom(cust, tc);
            // save in OWL/XML format
            if (o instanceof OWLMutableOntology) {
                if (!o.containsAxiom(ax)) {
                    AddAxiom addAx = new AddAxiom(o, ax);
                    m.applyChange(addAx);
                    m.saveOntology(o, new OWLXMLOntologyFormat(), IRI.create(Phy_Add_IRI));
                }
            }
            m.removeOntology(o);
        } catch (Exception e) {
            System.out.println("Could not create ontology: " + e.getMessage());
        }
    }

            public void saveStrDataPropertyAssertion(String domindv, String dtpName, String rangeSTRINGval) {
        try {
            o = m.loadOntologyFromOntologyDocument(IRI.create(Phy_Add_IRI));
            OWLNamedIndividual domain = f.getOWLNamedIndividual(IRI.create(Onto_Base_IRI + "#" + domindv));
            OWLDataProperty dproperty = f.getOWLDataProperty(IRI.create(Onto_Base_IRI + "#" + dtpName));
            OWLDataPropertyAssertionAxiom ax = f.getOWLDataPropertyAssertionAxiom(dproperty, domain, rangeSTRINGval);
            if (o instanceof OWLMutableOntology) {
                if (!o.containsAxiom(ax)) {
                    AddAxiom addAx = new AddAxiom(o, ax);
                    m.applyChange(addAx);
                    m.saveOntology(o, new OWLXMLOntologyFormat(), IRI.create(Phy_Add_IRI));
                }
            }
            m.removeOntology(o);
        } catch (Exception e) {
            System.out.println("Could not create ontology: " + e.getMessage());
        }
    }
            
     public void saveDoubleDataPropertyAssertion(String domindv, String dtpName, double rangeDOUBLEval) {
        try {
            o = m.loadOntologyFromOntologyDocument(IRI.create(Phy_Add_IRI));
            OWLNamedIndividual domain = f.getOWLNamedIndividual(IRI.create(Onto_Base_IRI + "#" + domindv));
            OWLDataProperty dproperty = f.getOWLDataProperty(IRI.create(Onto_Base_IRI + "#" + dtpName));
            OWLDataPropertyAssertionAxiom ax = f.getOWLDataPropertyAssertionAxiom(dproperty, domain, rangeDOUBLEval);
            if (o instanceof OWLMutableOntology) {
                if (!o.containsAxiom(ax)) {
                    AddAxiom addAx = new AddAxiom(o, ax);
                    m.applyChange(addAx);
                    m.saveOntology(o, new OWLXMLOntologyFormat(), IRI.create(Phy_Add_IRI));
                }
            }
            m.removeOntology(o);
        } catch (Exception e) {
            System.out.println("Could not create ontology: " + e.getMessage());
        }
    }
     
          public void saveIntegerDataPropertyAssertion(String domindv, String dtpName, int rangeINTEGERval) {
        try {
            o = m.loadOntologyFromOntologyDocument(IRI.create(Phy_Add_IRI));
            OWLNamedIndividual domain = f.getOWLNamedIndividual(IRI.create(Onto_Base_IRI + "#" + domindv));
            OWLDataProperty dproperty = f.getOWLDataProperty(IRI.create(Onto_Base_IRI + "#" + dtpName));
            OWLDataPropertyAssertionAxiom ax = f.getOWLDataPropertyAssertionAxiom(dproperty, domain, rangeINTEGERval);
            if (o instanceof OWLMutableOntology) {
                if (!o.containsAxiom(ax)) {
                    AddAxiom addAx = new AddAxiom(o, ax);
                    m.applyChange(addAx);
                    m.saveOntology(o, new OWLXMLOntologyFormat(), IRI.create(Phy_Add_IRI));
                }
            }
            m.removeOntology(o);
        } catch (Exception e) {
            System.out.println("Could not create ontology: " + e.getMessage());
        }
    }   
    
     public void saveObjPropertyAssertion(String domindv, String otpName, String rangeindv) {
        try {
            o = m.loadOntologyFromOntologyDocument(IRI.create(Phy_Add_IRI));
            OWLNamedIndividual dom = f.getOWLNamedIndividual(IRI.create(Onto_Base_IRI + "#" + domindv));
            OWLNamedIndividual range = f.getOWLNamedIndividual(IRI.create(Onto_Base_IRI + "#" + rangeindv));
            OWLObjectProperty oproperty = f.getOWLObjectProperty(IRI.create(Onto_Base_IRI + "#" + otpName));
            OWLObjectPropertyAssertionAxiom ax = f.getOWLObjectPropertyAssertionAxiom(oproperty, dom, range);
            if (o instanceof OWLMutableOntology) {
                if (!o.containsAxiom(ax)) {
                    AddAxiom addAx = new AddAxiom(o, ax);
                    m.applyChange(addAx);
                    m.saveOntology(o, new OWLXMLOntologyFormat(), IRI.create(Phy_Add_IRI));
                }
            }
            m.removeOntology(o);
        } catch (Exception e) {
            System.out.println("Could not create ontology: " + e.getMessage());
        }
    }
      public List getAnObjectProperty(String ind, String OTP) {
        List<String> list = new ArrayList<String>();

        try {
            o = m.loadOntologyFromOntologyDocument(Phy_Add_IRI);
            PelletReasoner r;
            r = PelletReasonerFactory.getInstance().createReasoner(o);
            OWLNamedIndividual individual = f.getOWLNamedIndividual(IRI.create(Onto_Base_IRI + "#" + ind));
            OWLObjectProperty op = f.getOWLObjectProperty(IRI.create(Onto_Base_IRI + "#" + OTP));
            NodeSet<OWLNamedIndividual> value = r.getObjectPropertyValues(individual, op);

            for (OWLNamedIndividual rangeVal : value.getFlattened()) {
                System.out.println(rangeVal.toString());
            }

            m.removeOntology(o);
        } catch (Exception e) {
            System.out.println("Could not create ontology: " + e.getMessage());
        }

        return list;
    }

    public static void main(String[] args) {
        // TODO code application logic here
        PSSIT call = new PSSIT();
        call.getAnObjectProperty("NUMPAD301", "hasPlace");
    }

}
