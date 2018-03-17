package sample;

import Model.ProgramState.PrgState;
import Model.Statements.*;
import Model.StringPair;
import Model.Utils.*;
import Model.Files.*;
import Model.Heap.*;
import Repository.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


public class RunProgram
{
    private IStatement statement;
    private IPrgStateRepo repo;
    private ExecutorService executor;
    private List<PrgState> prgs;

    public RunProgram(IStatement s)
    {
        executor = Executors.newFixedThreadPool(2);
        statement = s;
        repo = new PrgStateRepo("text.txt");
        repo.addPrgState(new PrgState(new ExeStack<>(), new Dictionary<>(), new outputList<>(), statement, new FileTable<>(), new Heap<>()));
        prgs = removeCompletedPrg(repo.getPrgList());
        prgs.forEach(p -> repo.logPrgStateExec(p));
    }

    public void initialize()
    {
        updateData();
        prgStatesList.setOnMouseClicked(x->updateData());
        heapTableAddress.setCellValueFactory(new PropertyValueFactory<StringPair, String>("first"));
        heapTableValue.setCellValueFactory(new PropertyValueFactory<StringPair, String>("second"));
        fileTableIdentifier.setCellValueFactory(new PropertyValueFactory<StringPair, String>("first"));
        fileTableFileName.setCellValueFactory(new PropertyValueFactory<StringPair, String>("second"));
        symbolTableVariable.setCellValueFactory(new PropertyValueFactory<StringPair, String>("first"));
        symbolTableValue.setCellValueFactory(new PropertyValueFactory<StringPair, String>("second"));
    }

    @FXML
    private TextField nrOfProgramStates;

    @FXML
    private TableView<StringPair> heapTable;

    @FXML
    private TableColumn<StringPair, String> heapTableAddress;

    @FXML
    private TableColumn<StringPair, String> heapTableValue;

    @FXML
    private ListView<String> outList;

    @FXML
    private TableView<StringPair> fileTable;

    @FXML
    private TableColumn<StringPair, String> fileTableIdentifier;

    @FXML
    private TableColumn<StringPair, String> fileTableFileName;

    @FXML
    private ListView<Integer> prgStatesList;

    @FXML
    private TableView<StringPair> symbolTable;

    @FXML
    private TableColumn<StringPair, String> symbolTableVariable;

    @FXML
    private TableColumn<StringPair, String> symbolTableValue;

    @FXML
    private ListView<String> exeStack;

    @FXML
    private Button oneStepButton;

    @FXML
    void buttonClicked(ActionEvent event)
    {
        try
        {
            if(prgs.size() > 0)
            {
                prgs = removeCompletedPrg(repo.getPrgList());
                oneStepForAllPrg(prgs);
                repo.setPrgList(prgs);
            }
        }
        catch (Exception e)
        {
            Alert a = new Alert(Alert.AlertType.ERROR, e.getMessage());
            a.show();
            Stage s = (Stage)oneStepButton.getScene().getWindow();
            s.close();
        }
        updateData();
    }

    private void updateExecStack(PrgState state)
    {
        ObservableList<String> li = FXCollections.observableArrayList();
        for(IStatement s : state.getExeStack().getAll())
            li.add(s.toString());
        exeStack.setItems(li);
    }

    private void updateSymbolTable(PrgState state)
    {
        ObservableList<StringPair> li = FXCollections.observableArrayList();
        for(Map.Entry<String, Integer> entry : state.getSymbolT().getAll())
        {
            li.add(new StringPair(entry.getKey(), entry.getValue().toString()));
        }
        symbolTable.setItems(li);
    }

    private void updateOutput(PrgState state)
    {
        ObservableList<String> li = FXCollections.observableArrayList();
        for(Integer i : state.getList().getAll())
            li.add(i.toString());
        outList.setItems(li);
    }

    private void updateFileTable(PrgState state)
    {
        ObservableList<StringPair> li = FXCollections.observableArrayList();
        for(Map.Entry<Integer, FileData> entry : state.getFileTable().getAll())
        {
            li.add(new StringPair(entry.getKey().toString(), entry.getValue().getFileName().toString()));
        }
        fileTable.setItems(li);
    }

    private void updateHeap(PrgState state)
    {
        ObservableList<StringPair> li = FXCollections.observableArrayList();
        for(Map.Entry<Integer, Integer> entry : state.getHeap().entrySet())
        {
            li.add(new StringPair(entry.getKey().toString(), entry.getValue().toString()));
        }
        heapTable.setItems(li);
    }

    private Integer updatePrgStates()
    {
        ObservableList<Integer> l = prgStatesList.getSelectionModel().getSelectedIndices();
        Integer id = null;
        if(l.size() == 1) id = prgStatesList.getItems().get(l.get(0));
        Integer newid = 0;
        ObservableList<Integer> newlist = FXCollections.observableArrayList();
        for(PrgState state : repo.getPrgList())
        {
            if (id != null && id == state.getId())
            {
                newid = newlist.size();
            }
            newlist.add(state.getId());
        }
        prgStatesList.setItems(newlist);
        if(newlist.size() > newid)
        {
            prgStatesList.getSelectionModel().select(newid);
            return newlist.get(newid);
        }
        return null;
    }

    private void updateData()
    {
        Integer id = updatePrgStates();
        List<PrgState> l = repo.getPrgList().stream().filter(x -> x.getId() == id).collect(Collectors.toList());
        if(l.size() == 0)
        {
            exeStack.setItems(FXCollections.observableArrayList());
            outList.setItems(FXCollections.observableArrayList());
            symbolTable.setItems(FXCollections.observableArrayList());
            heapTable.setItems(FXCollections.observableArrayList());
            fileTable.setItems(FXCollections.observableArrayList());
            System.out.println("The selected id does not exist");
            return;
        }

        PrgState state = l.get(0);
        updateExecStack(state);
        updateOutput(state);
        updateSymbolTable(state);
        updateFileTable(state);
        updateHeap(state);

        nrOfProgramStates.setText("Nr of program states: " + repo.getPrgList().size());
    }


        public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList)
    {
        return inPrgList.stream()
                .filter(p->p.isNotCompleted())
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<PrgState> prgList)
    {
        prgList.forEach(prg->repo.logPrgStateExec(prg));

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(() -> {return p.executeOneStep();}))
                .collect(Collectors.toList());

        try
        {
            List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                    .map(future ->
                    {
                        try
                        {
                            return future.get();
                        }
                        catch (Exception ex1)
                        {
                            System.out.println(ex1);
                        }
                        return null;
                    })
                    .filter(p -> p != null)
                    .collect(Collectors.toList());

            prgList.addAll(newPrgList);

            prgList.forEach(prg -> repo.logPrgStateExec(prg));

            repo.setPrgList(prgList);
        }
        catch (Exception ex2)
        {
            System.out.println(ex2);
        }
    }
}