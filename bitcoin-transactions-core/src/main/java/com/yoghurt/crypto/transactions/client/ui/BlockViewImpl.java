package com.yoghurt.crypto.transactions.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.yoghurt.crypto.transactions.client.util.BlockPartColorPicker;
import com.yoghurt.crypto.transactions.client.util.FormatUtil;
import com.yoghurt.crypto.transactions.client.widget.BlockHexViewer;
import com.yoghurt.crypto.transactions.client.widget.ValueViewer;
import com.yoghurt.crypto.transactions.shared.domain.Block;
import com.yoghurt.crypto.transactions.shared.domain.BlockInformation;
import com.yoghurt.crypto.transactions.shared.domain.BlockPartType;
import com.yoghurt.crypto.transactions.shared.domain.RawBlockContainer;
import com.yoghurt.crypto.transactions.shared.util.block.BlockEncodeUtil;

public class BlockViewImpl extends Composite implements BlockView {
  interface BlockViewImplUiBinder extends UiBinder<Widget, BlockViewImpl> {}

  private static final BlockViewImplUiBinder UI_BINDER = GWT.create(BlockViewImplUiBinder.class);

  @UiField FlowPanel extraInformationContainer;
  @UiField Label loadPlaceHolder;
  @UiField Label notFoundLabel;

  @UiField(provided = true) ValueViewer blockHashViewer;

  @UiField(provided = true) ValueViewer versionViewer;
  @UiField(provided = true) ValueViewer previousBlockHashViewer;
  @UiField(provided = true) ValueViewer merkleRootViewer;
  @UiField(provided = true) ValueViewer timestampViewer;
  @UiField(provided = true) ValueViewer bitsViewer;
  @UiField(provided = true) ValueViewer nonceViewer;

  @UiField(provided = true) ValueViewer heightViewer;
  @UiField(provided = true) ValueViewer numConfirmationsViewer;
  @UiField(provided = true) ValueViewer numTransactionsViewer;
  @UiField(provided = true) ValueViewer nextBlockViewer;
  @UiField(provided = true) ValueViewer sizeViewer;

  @UiField BlockHexViewer blockHexViewer;

  public BlockViewImpl() {
    blockHashViewer = new ValueViewer(BlockPartColorPicker.getFieldColor(BlockPartType.PREV_BLOCK_HASH));
    versionViewer = new ValueViewer(BlockPartColorPicker.getFieldColor(BlockPartType.VERSION));
    previousBlockHashViewer = new ValueViewer(BlockPartColorPicker.getFieldColor(BlockPartType.PREV_BLOCK_HASH));
    merkleRootViewer = new ValueViewer(BlockPartColorPicker.getFieldColor(BlockPartType.MERKLE_ROOT));
    timestampViewer = new ValueViewer(BlockPartColorPicker.getFieldColor(BlockPartType.TIMESTAMP));
    bitsViewer = new ValueViewer(BlockPartColorPicker.getFieldColor(BlockPartType.BITS));
    nonceViewer = new ValueViewer(BlockPartColorPicker.getFieldColor(BlockPartType.NONCE));

    heightViewer = new ValueViewer(BlockPartColorPicker.getFieldColor(BlockPartType.VERSION));
    numConfirmationsViewer = new ValueViewer(BlockPartColorPicker.getFieldColor(BlockPartType.VERSION));
    numTransactionsViewer = new ValueViewer(BlockPartColorPicker.getFieldColor(BlockPartType.MERKLE_ROOT));
    nextBlockViewer = new ValueViewer(BlockPartColorPicker.getFieldColor(BlockPartType.PREV_BLOCK_HASH));
    sizeViewer = new ValueViewer(BlockPartColorPicker.getFieldColor(BlockPartType.TIMESTAMP));

    initWidget(UI_BINDER.createAndBindUi(this));
  }

  @Override
  public void setBlock(final Block block) {
    blockHashViewer.setValue(block.getBlockHash());

    versionViewer.setValue(block.getVersion());
    previousBlockHashViewer.setValue(block.getPreviousBlockHash());
    merkleRootViewer.setValue(block.getMerkleRoot());
    timestampViewer.setValue(FormatUtil.formatDateTime(block.getTimestamp()));
    bitsViewer.setValue(block.getBits());
    nonceViewer.setValue(block.getNonce());

    final RawBlockContainer rawTransaction = new RawBlockContainer();
    try {
      BlockEncodeUtil.encodeBlock(block, rawTransaction);
    } catch (final Throwable e) {
      e.printStackTrace();
      // Eat.
    }

    blockHexViewer.setBlock(rawTransaction);
  }

  @Override
  public void setBlockInformation(final BlockInformation blockInformation) {
    loadPlaceHolder.removeFromParent();

    if (blockInformation == null) {
      notFoundLabel.setVisible(true);
      return;
    }

    extraInformationContainer.setVisible(true);

    heightViewer.setValue(blockInformation.getHeight());
    numConfirmationsViewer.setValue(blockInformation.getNumConfirmations());
    numTransactionsViewer.setValue(blockInformation.getNumTransactions());
    nextBlockViewer.setValue(blockInformation.getNextBlockHash());
  }
}