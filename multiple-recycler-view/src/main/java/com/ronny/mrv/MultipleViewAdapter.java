/**
 * MIT License
 * <p>
 * Copyright (c) 2019 吴荣 (Ronny Wu)
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.ronny.mrv;

import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * TODO 封装目标:
 * <p>
 * 1.               编译期可以自动生成注册代码?
 * 2. 自动支持多布局,返回多少个布局ID,自动注册多少个布局,至少一个.
 * 3. 支持 Manager 管理.
 * 4. 支持多 Manager 聚合管理.
 * 5. 支持下拉刷新,上拉加载更多.
 * 6. 支持自定义下拉刷新头和上拉加载更多脚.
 * 7. 支持自动刷新和自动加载更多.
 * 8. 支持两种侧滑模式的菜单.
 * 9. 多类型事件支持.
 * 10. 顺序数据源的聚合事件支持.
 * 11. 线条的支持.
 * 12. 多类型布局的支持.
 * 13. 自动生命周期期间mapping的自动维护,降低内存消耗.
 * 14. 多类型下标支持,完整下标,聚合下标,分类下标.
 * 15. 同分类的无序数据源聚合支持.
 * 16. 自动分类间隔支持,自动分类间隔配置.
 * 17. 回收事件处理,可配置某个位置的Item不回收,或者回收以后,自动缓存,避免复杂布局的重建影响列表的性能.
 * 18. 自动占位及异步刷新支持.
 * 19. 基于状态机的页面状态管理.(可单独成库,然后提供一个兼容库,可以自动集成到当前库中进行使用.)
 * 20. 布局切换的支持,可以自动切换布局,切换布局动画,列表快速消失,卡片快速进入.反之亦然.
 * 21. 元素共享动画实现布局切换,卡片原地变方或者卡片原地变成条目.
 * 22. 自动局部刷新.
 * <p>
 * 第一版: 一个 Class 绑定一个 Manager , Manager 可以绑定多个布局, 比如聊天界面.发送的消息和接受的消息.同样的实体数据,对应不同的布局.
 * 第二版: 一个 Class 绑定一个 Manager , Manager 可以绑定多个布局. 没变化?
 */
public class MultipleViewAdapter extends RecyclerView.Adapter {


    private int currentShowStyleModel;
    private static final int UNKNOWN_ITEM_VIEW_TYPE = 1;
    private static final int INVALID_ITEM_VIEW_DATA = 2;


    private List<Object> mOldDataSource;
    private List<Object> mAllDataSource;
    private ArrayMap<String, Manager> mManagersMapping;
    private DifferenceComparison mDifferenceComparison;

    public MultipleViewAdapter() {
        mOldDataSource = new ArrayList<>();
        mAllDataSource = new ArrayList<>();
        mManagersMapping = new ArrayMap<>();
        mDifferenceComparison = new DifferenceComparison();
    }

    public <T, MANAGER extends Manager<T>> void register(@NonNull Class<T> cls, @NonNull MANAGER manager) {

        mManagersMapping.put(cls.getName(), manager);
    }

    @Override
    public int getItemViewType(int position) {
        Object itemData = mAllDataSource.get(position);
        if (null != itemData) {
            String key = itemData.getClass().getName();
            Manager manager = mManagersMapping.get(key);
            if (null != manager) {
                // noinspection unchecked
                return manager.getLayoutResId(itemData, currentShowStyleModel);
            }
            return UNKNOWN_ITEM_VIEW_TYPE;
        }
        return INVALID_ITEM_VIEW_DATA;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case UNKNOWN_ITEM_VIEW_TYPE:
                return null;
            case INVALID_ITEM_VIEW_DATA:
                return null;
            default:
                return new MultipleViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object itemData = mAllDataSource.get(position);
        if (null != itemData) {
            String key = itemData.getClass().getName();
            Manager manager = mManagersMapping.get(key);
            if (null != manager) {
                // noinspection unchecked
                manager.onBindViewHolder((MultipleViewHolder) holder, itemData, position);
            } else {
                // TODO 没有 Manager 无法对数据进行复用操作.
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            Object itemData = mAllDataSource.get(position);
            if (null != itemData) {
                String key = itemData.getClass().getName();
                Manager manager = mManagersMapping.get(key);
                if (null != manager) {
                    // noinspection unchecked
                    manager.onPayloadViewHolder((MultipleViewHolder) holder, (JSONObject) payloads.get(position), position);
                } else {
                    // TODO 没有 Manager 无法对数据进行相关操作.
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return mAllDataSource.size();
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public void setAllDataSource(List<Object> allDataSource) {
        mOldDataSource.clear();
        mOldDataSource.addAll(mAllDataSource);
        
        mAllDataSource.clear();
        mAllDataSource.addAll(allDataSource);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(mDifferenceComparison, true);
        diffResult.dispatchUpdatesTo(this);
    }


    private class DifferenceComparison extends DiffUtil.Callback {

        @Override
        public int getOldListSize() {
            return mOldDataSource.size();
        }

        @Override
        public int getNewListSize() {
            return mAllDataSource.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            Object oldData = mOldDataSource.get(oldItemPosition);
            Object newData = mAllDataSource.get(newItemPosition);
            if (null != oldData && null != newData && oldData.getClass() == newData.getClass()) {
                String key = oldData.getClass().getName();
                Manager manager = mManagersMapping.get(key);
                if (null != manager) {
                    // noinspection unchecked
                    return manager.onItemComparison(oldData, newData);
                }
            }
            return false;
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            Object oldData = mOldDataSource.get(oldItemPosition);
            Object newData = mAllDataSource.get(newItemPosition);
            if (null != oldData && null != newData) {
                String key = oldData.getClass().getName();
                Manager manager = mManagersMapping.get(key);
                if (null != manager) {
                    // noinspection unchecked
                    return manager.onContentComparison(oldData, newData);
                }
            }
            return false;
        }

        @Nullable
        @Override
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            Object oldData = mOldDataSource.get(oldItemPosition);
            Object newData = mAllDataSource.get(newItemPosition);
            String managerKey = oldData.getClass().getName();
            Manager manager = mManagersMapping.get(managerKey);
            if (null != manager) {
                // noinspection unchecked
                return manager.onPayloadComparison(oldData, newData);
            }
            return null;
        }
    }
}