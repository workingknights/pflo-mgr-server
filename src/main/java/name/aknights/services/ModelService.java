package name.aknights.services;

import name.aknights.api.Model;
import name.aknights.api.ModelEntry;
import name.aknights.db.ModelDAO;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class ModelService {

    private static final Logger logger = LoggerFactory.getLogger(ModelService.class);

    private ModelDAO modelDAO;

    @Inject
    public ModelService(ModelDAO modelDAO) {
        this.modelDAO = modelDAO;
    }

    public Model getModelForUser(String userId) {
        return modelDAO.getModelForUser(userId);
    }

    public void addModelEntry(String modelId, ModelEntry modelEntry) {
        Model model = modelDAO.get(new ObjectId(modelId));

        model.getEntries().removeIf(entry -> entry.getTicker().equals(modelEntry.getTicker()));
        model.getEntries().add(modelEntry);

        modelDAO.save(model);
    }

    public String createModel(Model model) {
        return modelDAO.saveAndReturn(model).toString();
    }

    public void updateModel(String modelId, Model model) {
        if (model.getId() == null)
            model.setId(modelId);

        modelDAO.save(model);
    }

    public Model getById(String modelId) {
        return modelDAO.get(new ObjectId((modelId)));
    }

}
