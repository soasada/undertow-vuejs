import {shallowMount} from '@vue/test-utils';
import CRUDTable from '@/components/CRUDTable.vue';

describe('CRUDTable.vue', () => {
    const columnNames = ['id', 'name'];
    const tableData = [
        {id: 1, name: 'Test'},
        {id: 2, name: 'Test2'},
        {id: 3, name: 'Test3'},
        {id: 4, name: 'Test4'},
        {id: 5, name: 'Test5'},
        {id: 6, name: 'Test6'},
        {id: 7, name: 'Test7'},
        {id: 8, name: 'Test8'},
        {id: 9, name: 'Test9'},
        {id: 10, name: 'Test10'},
        {id: 11, name: 'Test11'}
    ];

    it('renders only ten elements', () => {
        const wrapper = shallowMount(CRUDTable, {
            propsData: {
                columnNames: columnNames,
                tableData: tableData
            }
        });

        expect(wrapper.text()).toMatch('Total: 11');
        expect(wrapper.text()).not.toMatch('Test11');
    });

    it('renders only the searched element', () => {
        const wrapper = shallowMount(CRUDTable, {
            propsData: {
                columnNames: columnNames,
                tableData: tableData
            }
        });

        wrapper.find('input').setValue('Test11');

        expect(wrapper.text()).toMatch('Test11');
        expect(wrapper.text()).not.toMatch('Test2');
    });
});
